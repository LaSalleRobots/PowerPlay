package org.firstinspires.ftc.teamcode.OpModes.Tele;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Lift;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Util.PIDController;

@TeleOp
public class Tuner extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(hardwareMap, new ElapsedTime());
        robot.imu.resetYaw();

        double tuningVariable = 0.0000001;
        double targetAngleDegrees = 0;

        Lift.Debouncer db = new Lift.Debouncer();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Tuning Var", "%f", tuningVariable);
            telemetry.update();
            while (gamepad1.x) {
                PIDController pid = new PIDController(
                        0.002, // Kp
                        0, // Ki
                        tuningVariable, // Kd
                        targetAngleDegrees // our target
                );

                double tmpTarget = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)
                        - targetAngleDegrees;

                while (true) {
                    double gyroAngle = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
                    double error = ((targetAngleDegrees - gyroAngle) % 360) - 180; // calculate our error

                    double pidVal = pid.update(error);
                    robot.drive.calculateDirectionsRobotCentric(0, 0, pidVal);

                    robot.drive.applyPower();

                    if (Math.abs(tmpTarget - (gyroAngle)) < .5
                            && Math.abs(robot.imu.getRobotAngularVelocity(AngleUnit.DEGREES).zRotationRate) < 1) {
                        break;
                    }
                }
                robot.drive.off();
            }
            if (db.isPressed(gamepad1.dpad_up)) {
                tuningVariable += 0.000000001;
            }

            if (db.isPressed(gamepad1.dpad_down)) {
                tuningVariable -= 0.000000001;
            }
        }
    }
}
