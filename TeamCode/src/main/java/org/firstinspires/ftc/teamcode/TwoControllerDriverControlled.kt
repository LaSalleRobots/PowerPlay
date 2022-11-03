package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.Debouncer.isPressed
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import kotlin.Throws
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Debouncer
import org.firstinspires.ftc.teamcode.TwoControllerDriverControlled.LiftControlMode

@TeleOp(name = "Two-Controller Driver-Controlled")
class TwoControllerDriverControlled : LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        val time = ElapsedTime()
        val robot = Robot(hardwareMap, time)
        var x: Double
        var y: Double
        var r: Double
        var target: Double

        // Debouncers
        val dx = Debouncer()
        val bumper = Debouncer()
        val clawdebouncer = Debouncer()
        val turnAroundDebouncer = Debouncer()

        var fieldCentric = false
        var liftControlMode = LiftControlMode.ManualControl
        //Quickly tweak sensitivity coefficients here
        val gpad1MoveSpeed = .9
        val gpad1RotationSpeed = 0.6
        val gpad2MoveSpeed = 0.0
        val gpad2RotationSpeed = 0.0
        //negative coefficient to account for backwards lift control. Invert as needed.
        val ManualModeLiftSensitivity = -50.0
        waitForStart()
        robot.lift.setPositionAsync(0)

        val MakerIndustriesIsTheBest = true
        while (MakerIndustriesIsTheBest) {
            telemetry.addData("Target", robot.lift.target)
            telemetry.addData("Position", robot.lift.position)
            telemetry.addData("Front Right", robot.drive.rightFront.currentPosition)
            telemetry.addData("Front Left", robot.drive.leftFront.currentPosition)
            telemetry.addData("Back Right", robot.drive.rightBack.currentPosition)
            telemetry.addData("Back Left", robot.drive.leftBack.currentPosition)

            //Movement section

            //Prematurely combining movement values for both slow mode and non-slow mode
            if (gamepad1.right_bumper) { //slow mode
                x = 0.5 * gpad1MoveSpeed * gamepad1.left_stick_x
                y = 0.5 * gpad1MoveSpeed * gamepad1.left_stick_y
                r = 0.75 * gpad1RotationSpeed * gamepad1.right_stick_x
            } else { //normal mode. Dual-gamepad control functionality is implemented here, but currently dormant.
                x = gpad1MoveSpeed * gamepad1.left_stick_x + gpad2MoveSpeed * gamepad2.left_stick_x
                y = gpad1MoveSpeed * gamepad1.left_stick_y + gpad2MoveSpeed * gamepad2.left_stick_y
                r = gpad1RotationSpeed * gamepad1.right_stick_x + gpad2RotationSpeed * gamepad2.right_stick_x
            }

            //Applies calculated movement for both field-centric and not
            if (fieldCentric) {
                //robot.getHeading() may be partially or not at all functional. Good luck, traveler.
                robot.drive.calculateDirectionsFieldCentric(x, y, -r, robot.heading)
            } else {
                //applies drive values. Notice the negative R.
                robot.drive.calculateDirections(x, y, -r)
            }

            //Applies... power or something. I think this works both for field-centric and not.
            robot.drive.applyPower()

            //Toggle field centric mode
            //First implementation of new debouncer. If this fails at competition just delete.
            if (dx.isPressed(gamepad1.triangle || gamepad1.y)) {
                fieldCentric = !fieldCentric
            }

            //Lift section
            when (liftControlMode) {
                LiftControlMode.ManualControl -> {
                    if (gamepad2.right_stick_y > 0.1 || gamepad2.right_stick_y < -0.1) {
                        target = robot.lift.position + ManualModeLiftSensitivity * gamepad2.right_stick_y
                        robot.lift.setPositionAsync(target.toInt())
                    }

                    //state machine exit condition
                    if (gamepad2.dpad_up || gamepad2.dpad_down ||
                            gamepad2.dpad_right || gamepad2.dpad_left || gamepad2.left_bumper || gamepad2.right_bumper) {
                        liftControlMode = LiftControlMode.PresetControl
                    }
                }
                LiftControlMode.PresetControl -> {
                    // operation while in Preset Control state
                    when {
                        gamepad2.dpad_up -> robot.lift.setPositionAsync(robot.lift.LARGE)
                        gamepad2.dpad_right -> robot.lift.setPositionAsync(robot.lift.SMALL)
                        gamepad2.dpad_left -> robot.lift.setPositionAsync(robot.lift.MIDDLE)
                        gamepad2.dpad_down -> robot.lift.setPositionAsync(0)
                        bumper.isPressed(gamepad2.left_bumper) -> robot.lift.setPositionAsync(robot.lift.target + 10) // slight bump up for cone stacks
                        bumper.isPressed(gamepad2.right_bumper) -> robot.lift.setPositionAsync(robot.lift.target - 10)
                        gamepad2.right_stick_y > 0.25 || gamepad2.right_stick_y < -0.25 || gamepad2.b -> liftControlMode = LiftControlMode.ManualControl  // state exit condition
                    }
                }
            }
            //Claw

            clawdebouncer.ifPressed(gamepad2.cross) { robot.grabber.toggle() }
            turnAroundDebouncer.ifPressed(gamepad1.b) { robot.drive.rotateRight() }

            if (!opModeIsActive()) {
                break
            }
            telemetry.update()
        }
    }

    private enum class LiftControlMode {
        ManualControl, PresetControl
    }
}