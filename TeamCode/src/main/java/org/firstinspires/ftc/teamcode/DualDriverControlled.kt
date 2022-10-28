package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.util.ElapsedTime

@Autonomous(name="DualDriver (KT)")
class DualDriverControlled: LinearOpMode() {

    enum class LiftControlMode {ManualControl, PresetControl, HoldPosition, Home}

    val G1SPEED = 0.8;
    val G2SPEED = 0.4;
    val G1ROTATE = 0.4;
    val G2ROTATE = 0.2;
    val LIFT_S = 50; // unit ticks

    override fun runOpMode() {
        // Configure Hardware
        val time = ElapsedTime();
        val robot = Robot(hardwareMap, time);

        // Robot States
        val MakerIndustriesIsTheBest = true;
        var fieldMode = false;
        var liftState = LiftControlMode.ManualControl;

        // Debouncers
        val dx = Debouncer();
        val df = Debouncer();


        waitForStart();

        while (MakerIndustriesIsTheBest) {
            // this shouldnt need to be here. we could move this kind of logic to some constants in the robot class
            val x = ((G1SPEED * gamepad1.left_stick_x) + (G2SPEED * gamepad2.left_stick_x))
            val y = ((G1SPEED * gamepad1.left_stick_y) + (G2SPEED * gamepad2.left_stick_y))
            val r = ((G1ROTATE * gamepad1.right_stick_x) + (G2ROTATE * gamepad2.right_stick_y))

            if (fieldMode) {
                robot.drive.calculateDirectionsFieldCentric(x,y,-r, robot.heading)
            } else {
               robot.drive.calculateDirections(x,y,-r);
            }
            robot.drive.applyPower();
            if (df.isPressed(gamepad1.triangle)) {
                fieldMode = !fieldMode
            }


            // manage lift states
            when (liftState) {
                LiftControlMode.Home -> {
                    if (!robot.lift.isActive) robot.lift.setPower(0.0)

                    if (dPad(gamepad2) || gamepad2.left_bumper) {
                        liftState = LiftControlMode.PresetControl
                        robot.lift.setPower(1.0)
                    }
                    if (stickMoved(gamepad2)) {
                        liftState = LiftControlMode.ManualControl
                        robot.lift.setPower(1.0);
                    }
                }
                LiftControlMode.ManualControl -> {
                    robot.lift.setPositionAsync(
                        (robot.lift.position + (-LIFT_S * gamepad2.right_stick_y)).toInt()
                    )

                    if (dPad(gamepad2) || gamepad2.left_bumper) liftState = LiftControlMode.PresetControl
                    if (gamepad2.right_stick_y == 0.toFloat()) liftState = LiftControlMode.HoldPosition
                }
                LiftControlMode.PresetControl -> {
                    when {
                        gamepad2.dpad_up -> robot.lift.setPositionAsync(robot.lift.LARGE)
                        gamepad2.dpad_down -> robot.lift.setPositionAsync(robot.lift.SMALL)
                        gamepad2.dpad_left -> robot.lift.setPositionAsync(robot.lift.MIDDLE)
                        gamepad2.dpad_right -> robot.lift.setPositionAsync(0)
                        gamepad2.left_bumper -> robot.lift.setPositionAsync(100) // small stack or smth
                    }

                   if (stickMoved(gamepad2) || gamepad2.b) liftState = LiftControlMode.ManualControl
                   if (!robot.lift.isActive)  liftState = LiftControlMode.HoldPosition
                }
                LiftControlMode.HoldPosition -> {
                    robot.lift.setPositionAsync(robot.lift.target)


                    if (stickMoved(gamepad2)) liftState = LiftControlMode.ManualControl
                    if (dPad(gamepad2) || gamepad2.left_bumper) liftState = LiftControlMode.PresetControl
                }
            }
            // only turn the motor off if we are under 10 position and we intend to be under ten
            if (robot.lift.position < 10 && robot.lift.target < 10) {
                liftState = LiftControlMode.Home
            }

            if (dx.isPressed(gamepad2.a)) {
                robot.grabber.toggle();
            }

            if (!opModeIsActive()) {break;}
        }
    }

    fun dPad(g: Gamepad): Boolean {
        return g.dpad_up || g.dpad_down ||
                g.dpad_right || g.dpad_left
    }
    fun stickMoved(g: Gamepad): Boolean {
        return g.right_stick_y > 0.1 || g.right_stick_y < -0.1
    }

}