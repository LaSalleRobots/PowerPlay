package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime

@Autonomous(name = "Kotlin Driver")
class KotlinDriver : LinearOpMode() {

    enum class LiftControlMode { ManualControl, PresetControl }

    override fun runOpMode() {
        val time = ElapsedTime()
        val robot = Robot(hardwareMap, time)

        val grabberDebouncer = Debouncer(10)

        var liftControlMode = LiftControlMode.ManualControl

        waitForStart()

        while (opModeIsActive()) {
            // Movement section // this should
            // Note that both gamepads get control over robot movement, and can complement or counteract each other.
            // prematurely combines joystick values, this is purely organizational
            val x = 0.75 * gamepad1.left_stick_x + 0.25 * gamepad2.left_stick_x
            val y = 0.75 * gamepad1.left_stick_y + 0.25 * gamepad2.left_stick_y
            val r = 0.25 * gamepad1.right_stick_x + 0.05 * gamepad2.right_stick_x
            //applies drive values. Notice the negative R.
            robot.drive.calculateDirections(x, y, -r)
            robot.drive.applyPower()


            // Handle Lift
            when (liftControlMode) {
                LiftControlMode.ManualControl -> {
                    // operation while in Manual Control state
                    val target = robot.lift.position + (50 * gamepad2.right_stick_y).toInt()
                    robot.lift.setPositionAsync(target)

                    // state exit condition
                    if (gamepad2.dpad_right || gamepad2.dpad_up || gamepad2.dpad_down || gamepad2.dpad_left) {
                        liftControlMode = LiftControlMode.PresetControl
                    }
                }
                LiftControlMode.PresetControl -> {
                    // operation while in Preset Control state
                    when {
                        gamepad2.dpad_up -> robot.lift.setPositionAsync(robot.lift.LARGE)
                        gamepad2.dpad_right -> robot.lift.setPositionAsync(robot.lift.MIDDLE)
                        gamepad2.dpad_down -> robot.lift.setPositionAsync(robot.lift.SMALL)
                    }

                    // state exit condition
                    if (!robot.lift.isActive) liftControlMode = LiftControlMode.ManualControl
                }
            }

            // Handle Grabber
            when {
                grabberDebouncer.isPressed(gamepad2.x || gamepad2.cross) -> robot.grabber.toggle()
            }

        }
    }
}