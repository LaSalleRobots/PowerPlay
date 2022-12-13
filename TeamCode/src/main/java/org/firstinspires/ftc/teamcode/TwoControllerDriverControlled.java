package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@TeleOp(name="Two-Controller Driver-Controlled")
public class TwoControllerDriverControlled extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();
        Robot robot = new Robot(hardwareMap, time);
        double x, y, r, target, targetAngle;

        //Debouncers
        Debouncer dx = new Debouncer(),
        bumper = new Debouncer(),
        clawdebouncer = new Debouncer(),
        turnAroundDebouncer = new Debouncer(),
        gyroAssistDebouncer = new Debouncer();

        //Coefficients
        double gpad1MoveSpeed = 1;
        double gpad1RotationSpeed = 0.7;
        double gpad2MoveSpeed = 0.0;
        double gpad2RotationSpeed = 0.0;
        double joystickSmoothingExp = 1.6;
        //negative lift coefficient. Change as needed.
        double ManualModeLiftSensitivity = -80;

        LiftControlMode liftControlMode = LiftControlMode.ManualControl;

        waitForStart();
        robot.lift.setPositionAsync(0);

        while (true) {
            if (!opModeIsActive()) {break;}
            //Telemetry
            telemetry.update();
            telemetry.addData("Pole: ", robot.poleSensor.getDistance(DistanceUnit.CM));
            telemetry.addData("IMU heading: ", robot.getHeading());
            telemetry.addData("Target", robot.lift.getTarget());
            telemetry.addData("Position", robot.lift.getPosition());
            telemetry.addData("Front Right", robot.drive.rightFront.getCurrentPosition());
            telemetry.addData("Front Left", robot.drive.leftFront.getCurrentPosition());
            telemetry.addData("Back Right", robot.drive.rightBack.getCurrentPosition());
            telemetry.addData("Back Left", robot.drive.leftBack.getCurrentPosition());

            //Movement section
            {
                //Prematurely combining movement values for both slow mode and non-slow mode
                if (gamepad1.right_bumper) { //slow mode
                    x = (0.75 * gpad1MoveSpeed * gamepad1.left_stick_x);
                    y = (0.75 * gpad1MoveSpeed * gamepad1.left_stick_y);
                    r = (0.75 * gpad1RotationSpeed * gamepad1.right_stick_x);
                }
                else { //normal mode. Dual-gamepad control functionality is implemented here, but currently dormant.
                    x = ((gpad1MoveSpeed * gamepad1.left_stick_x) + (gpad2MoveSpeed * gamepad2.left_stick_x));
                    y = ((gpad1MoveSpeed * gamepad1.left_stick_y) + (gpad2MoveSpeed * gamepad2.left_stick_y));
                    r = ((gpad1RotationSpeed * gamepad1.right_stick_x) + (gpad2RotationSpeed * gamepad2.right_stick_x));
                }

                x = applyJoystickSmoothing(x, joystickSmoothingExp);
                y = applyJoystickSmoothing(y, joystickSmoothingExp);
                r = applyJoystickSmoothing(r, joystickSmoothingExp);

                //employs gyro stabilization to linear driving
                if (Math.abs(gamepad1.right_stick_x) < 0.05) {
                    if (gyroAssistDebouncer.isPressed(Math.abs(gamepad1.right_stick_y) < 0.05)) {
                        //identifies the desired heading angle to hold. This should only happen once per period of time that the right joystick is < 0.05
                        target = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
                    }
                    robot.drive.calcGyroStabilized(x, y, target);
                }
                else {
                    robot.drive.calculateDirections(x, y, r);
                }
                //90 degree turn left/right
                if (gamepad1.left_trigger > 0.5) {
                    if (turnAroundDebouncer.isPressed(gamepad1.left_trigger > 0.5)){
                        target = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - 90;
                    }
                    robot.drive.calcGyroStabilized(0, 0, target);
                }
                if (gamepad1.right_trigger > 0.5) {
                    if (turnAroundDebouncer.isPressed(gamepad1.right_trigger > 0.5)){
                        target = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + 90;
                    }
                    robot.drive.calcGyroStabilized(0, 0, target);
                }
                //180 degree turn
                if (gamepad1.b) {
                    if (turnAroundDebouncer.isPressed(gamepad1.b)) {
                        target = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + 180;
                    }
                    robot.drive.calcGyroStabilized(0, 0, target);
                }

                robot.drive.applyPower();
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
                robot.sleep(.5);
                robot.lift.setPosition(robot.lift.getPosition()+500);
                robot.sleep(.1);
                robot.drive.backward().goFor(0.5);
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
