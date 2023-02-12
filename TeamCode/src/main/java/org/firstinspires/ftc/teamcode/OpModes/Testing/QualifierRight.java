package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Vision;

@Autonomous(name="Qualifier Right Autonomous")


public class QualifierRight extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        robot.drive.speed = 0.35;

        final double inchesPerBox = robot.inchesPerBox;
        final double robotLength = robot.robotLength;
        final int directionCoefficient = -1;
        // -1 means right


        int id = 3;

        while (opModeInInit()) {
            id = robot.vision.getIdentifier();
            telemetry.addData("Id:", id);

            telemetry.update();
        }

        if (id == -1) { id = 3; }


        waitForStart();
        robot.imu.resetYaw();


        robot.grabber.close();
        robot.sleep(0.25);
        robot.lift.setPositionAsync(robot.lift.MIDDLE);

        robot.drive.forward().goDist(4, 0.5);
        robot.sleep(0.25);

        robot.drive.left().goDist(24, 0.5);
        robot.sleep(0.25);

        robot.drive.forward().goDist(26.5, 0.5);
        robot.sleep(0.25);

        robot.drive.rotateGyro(-90, -90);
        robot.sleep(0.25);

        robot.deliver();
        robot.sleep(0.25);

        robot.drive.left().goDist(12, 0.5);
        robot.sleep(0.25);

        robot.drive.rotateGyro(0, -90);
        robot.sleep(0.25);

        robot.lift.setPositionAsync(robot.lift.FIVE_STACK);

        robot.drive.forward().goDist(47, 0.5);
        robot.sleep(0.25);

        robot.drive.bumperGoDist(12, robot.bumpSensorLeft, robot.bumpSensorRight);
        robot.sleep(0.25);

        robot.grabber.close();
        robot.sleep(0.25);

        robot.lift.setPosition(robot.lift.FIVE_STACK + robot.lift.ONE_STACK);
        robot.sleep(0.25);

        robot.lift.setPositionAsync(robot.lift.LARGE);

        robot.drive.backward().goDist(35, 0.5);
        robot.sleep(0.25);

        robot.drive.rotateGyro(90,0);
        robot.sleep(0.25);

        robot.deliver();
        robot.sleep(0.25);

        robot.lift.setPositionAsync(5);

        robot.drive.rotateGyro(-90, -90);
        robot.sleep(0.25);

        if (id == 3) {
            robot.drive.forward().goDist(31, 0.5);
        }
        else if (id ==2) {
            robot.drive.forward().goDist(12, 0.5);
        }
        else {
            robot.drive.backward().goDist(9, 0.5);
        }
        robot.drive.brake();

    }
}
