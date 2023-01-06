package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Vision;

@Autonomous
public class AutoHybridTurn extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);
        robot.drive.speed = 0.35;

        Vision vision = new Vision(hardwareMap);

        final double inchesPerBox = robot.inchesPerBox;
        final double robotLength = robot.robotLength;
        final int directionCoefficient = -1;
        // -1 means right

        int id = 3;

        while (opModeInInit()) {
            id = vision.getIdentifier();
            telemetry.addData("Id:", id);
            telemetry.update();
        }

        if (id == -1) { id = 3; }

        waitForStart();
        robot.imu.resetYaw();


        int heading = -90;


        robot.drive.rotateLeftEncoder(heading);
        robot.sleep(.25);


        while(Math.abs(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - heading) >= 0.5){
            telemetry.addData("pos:", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.update();
            if(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) < heading){
                robot.drive.calculateDirections(0,0,-0.4);
                robot.drive.applyPower();
                //robot.drive.runToPositionIgnoreRight(robot.drive.leftFront.getCurrentPosition() + 10,robot.drive.rightFront.getCurrentPosition() - 10,robot.drive.leftBack.getCurrentPosition() + 10,robot.drive.rightBack.getCurrentPosition() - 10);
            }
            else {
                if(!(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > heading)) {
                    robot.drive.calculateDirections(0,0,0);
                    robot.drive.applyPower();
                }
            if(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > heading){
                robot.drive.calculateDirections(0,0,0.4);
                robot.drive.applyPower();
            }
            else {
                if(!(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) < heading)) {
                    robot.drive.calculateDirections(0,0,0);
                    robot.drive.applyPower();
                }
            }
                //robot.drive.runToPositionIgnoreLeft(robot.drive.leftFront.getCurrentPosition() - 10,robot.drive.rightFront.getCurrentPosition() + 10,robot.drive.leftBack.getCurrentPosition() - 10,robot.drive.rightBack.getCurrentPosition() + 10);

            }
        }
    }
}
