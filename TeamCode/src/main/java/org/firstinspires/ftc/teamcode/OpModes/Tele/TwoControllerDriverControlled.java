package org.firstinspires.ftc.teamcode.OpModes.Tele;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Util.Debouncer;

@TeleOp(name="Two-Controller Driver-Controlled")
public class TwoControllerDriverControlled extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();
        Robot robot = new Robot(hardwareMap, time);
        double x, y, r, target, targetAngle;

        // Debouncers
        Debouncer dx = new Debouncer(),
                bumper = new Debouncer(),
                clawdebouncer = new Debouncer(),
                turnAroundDebouncer = new Debouncer();

        boolean fieldCentric = false;
        LiftControlMode liftControlMode = LiftControlMode.ManualControl;

        //Quickly tweak sensitivity coefficients here
        double gpad1MoveSpeed = .9;
        double gpad1RotationSpeed = 0.7;
        double gpad2MoveSpeed = 0.0;
        double gpad2RotationSpeed = 0.0;

        // Exp for smoothing joystick values
        double joystickSmoothingExp = 1.6;


        //negative coefficient to account for backwards lift control. Invert as needed.
        double ManualModeLiftSensitivity = -80;

        waitForStart();
        time.reset();
        robot.lift.setPositionAsync(0);

        while (true) {

            /* if (time.seconds() > 89) {
                robot.grabber.open(); } */

            if (!opModeIsActive()) {break;}
            telemetry.update();

            telemetry.addData("Pole: ", robot.poleSensor.getDistance(DistanceUnit.CM));
            telemetry.addData("IMU heading: ", robot.getAngles());
            telemetry.addData("Target", robot.lift.getTarget());
            telemetry.addData("Position", robot.lift.getPosition());

            telemetry.addData("Front Right", robot.drive.rightFront.getCurrentPosition());
            telemetry.addData("Front Left", robot.drive.leftFront.getCurrentPosition());
            telemetry.addData("Back Right", robot.drive.rightBack.getCurrentPosition());
            telemetry.addData("Back Left", robot.drive.leftBack.getCurrentPosition());


            //Movement section
            {
                //Prematurely combining movement values for both slow mode and non-slow mode
                if (gamepad1.left_bumper) { // Slow Mode
                    x = (0.65 * gpad1MoveSpeed * gamepad1.left_stick_x);
                    y = (0.65 * gpad1MoveSpeed * gamepad1.left_stick_y);
                    r = (0.65 * gpad1RotationSpeed * gamepad1.right_stick_x);
                }
                else if (gamepad1.right_bumper) {  // Boost
                    x = ((1.75 * gpad1MoveSpeed * gamepad1.left_stick_x));
                    y = ((1.75 * gpad1MoveSpeed * gamepad1.left_stick_y));
                    r = ((1.75 * gpad1RotationSpeed * gamepad1.right_stick_x));
                }
                else { // Normal mode
                    x = (.9 * gpad1MoveSpeed * gamepad1.left_stick_x);
                    y = (.9 * gpad1MoveSpeed * gamepad1.left_stick_y);
                    r = (.9 * gpad1RotationSpeed * gamepad1.right_stick_x);
                }

                x = applyJoystickSmoothing(x, joystickSmoothingExp);
                y = applyJoystickSmoothing(y, joystickSmoothingExp);
                r = applyJoystickSmoothing(r, joystickSmoothingExp);



                //Applies calculated movement for both field-centric and not
                if (fieldCentric) {
                    //robot.getHeading() may be partially or not at all functional. Good luck, traveler.
                    robot.drive.calculateDirectionsFieldCentric(x, y, r, robot.getHeading());
                }
                else {
                    //applies drive values. Notice the negative R.
                    robot.drive.calculateDirections(x, y, r);
                }
                //Applies... power or something. I think this works both for field-centric and not.
                robot.drive.applyPower();

                //Toggle field centric mode
                //First implementation of new debouncer. If this fails at competition just delete.
                if (dx.isPressed(gamepad1.triangle || gamepad2.x)) {
                    fieldCentric = !fieldCentric;
                }

                //180
                if (turnAroundDebouncer.isPressed(gamepad1.b)) {
                    robot.drive.rotateLeftEncoder(180);
                }

                //90
                if (turnAroundDebouncer.isPressed(gamepad1.left_trigger > .5)) {
                    robot.drive.rotateLeftEncoder(90);
                }
                if (turnAroundDebouncer.isPressed(gamepad1.right_trigger > 0.5)) {
                    robot.drive.rotateRightEncoder(90);
                }
            }

            if (!opModeIsActive()) {break;}
            telemetry.update();

            //Lift section
            {
                if (liftControlMode == LiftControlMode.ManualControl) {
                    // operation while in Manual Control state
                    if (gamepad2.right_stick_y > 0.1 || gamepad2.right_stick_y < -0.1) {
                        target = (robot.lift.getPosition() + (ManualModeLiftSensitivity * gamepad2.right_stick_y));
                        robot.lift.setPositionAsync((int) target);


                    }

                    //state machine exit condition
                    if (gamepad2.dpad_up || gamepad2.dpad_down ||
                            gamepad2.dpad_right || gamepad2.dpad_left || gamepad2.left_bumper || gamepad2.right_bumper) {
                        liftControlMode = LiftControlMode.PresetControl;
                    }
                }

                if (!opModeIsActive()) {break;}
                telemetry.update();

                if (liftControlMode == LiftControlMode.PresetControl) {
                    // operation while in Preset Control state
                    if (gamepad2.dpad_up) {
                        robot.lift.setPositionAsync(robot.lift.LARGE);
                    }
                    if (gamepad2.dpad_right) {
                        robot.lift.setPositionAsync(robot.lift.SMALL);
                    }
                    if (gamepad2.dpad_left) {
                        robot.lift.setPositionAsync(robot.lift.MIDDLE);
                    }
                    if (gamepad2.dpad_down) {
                        robot.lift.setPositionAsync(0);
                    }

                    // slight bump up for cone stacks
                    if (bumper.isPressed(gamepad2.left_bumper)) {
                        robot.lift.setPositionAsync(robot.lift.getTarget() + 10);
                    }
                    if (bumper.isPressed(gamepad2.right_bumper)) {
                        robot.lift.setPositionAsync(robot.lift.getTarget() - 10);
                    }

                    // state exit condition
                    if (gamepad2.right_stick_y > 0.25 || gamepad2.right_stick_y < -0.25 || gamepad2.b) {
                        liftControlMode = LiftControlMode.ManualControl;
                    }
                }
            }

            if (!opModeIsActive()) {break;}
            telemetry.update();

            //Claw
            if (clawdebouncer.isPressed(gamepad2.cross)) {
                robot.grabber.toggle();
            }

            if (!opModeIsActive()) {break;}
            telemetry.update();


            if (robot.bumperPressed()) {
                robot.grabber.close();
                robot.sleep(.25);
                robot.lift.setPosition(robot.lift.getPosition() + 500);
                robot.sleep(.1);
                robot.drive.backward().goDist(0.3   * robot.inchesPerBox);
                robot.sleep(.1);
            }




            if (!opModeIsActive()) {break;}
            telemetry.update();
        }
    }

    private double applyJoystickSmoothing(double n, double a) {
        if (n >= 0) {
            return Math.pow(n, a);
        }
        else {
            return -Math.pow(-n, a);
        }
    }
    private enum LiftControlMode { ManualControl, PresetControl }
}