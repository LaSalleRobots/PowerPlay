package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Vision;

@Autonomous(group = "Auto")
public class AutoPoleSensingRight extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        robot.drive.speed = 0.55;

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

        robot.vision.switchToPolesMode();
        waitForStart();

        robot.imu.resetYaw();

        robot.grabber.close();
        robot.sleep(0.25);

        robot.lift.setPositionAsync(robot.lift.FIVE_STACK);

        robot.drive.forward().goDist(.1, 0.55);

        robot.sleep(.05);

        robot.drive.left().goDist(1 * inchesPerBox, 0.55);

        robot.sleep(.5);

        robot.lift.setPositionAsync(robot.lift.LARGE);

        robot.drive.speed = 0.75;

        robot.drive.forward().goDistSmooth(2.15 * inchesPerBox, 0.75);

        robot.sleep(.25);

        robot.drive.speed = .35;

        robot.drive.rotateRightEncoder(45);

        robot.drive.speed = .45;

        robot.drive.forward().goDist(.15 * inchesPerBox, 0.45);

        robot.sleep(0.05);

        robot.poleHarmonization(telemetry);

        double p = 0.35;
        robot.drive.leftFront.setPower(p);
        robot.drive.rightFront.setPower(p);
        robot.drive.leftBack.setPower(p);
        robot.drive.rightBack.setPower(p);

        robot.sleep(.05);

        robot.deliver();

        robot.drive.leftFront.setPower(p);
        robot.drive.rightFront.setPower(p);
        robot.drive.leftBack.setPower(p);
        robot.drive.rightBack.setPower(p);

        robot.sleep(.05);

        robot.drive.backward().goDist(.2 * inchesPerBox, 0.45);

        robot.sleep(.05);
        robot.drive.speed = .55;

        robot.drive.rotateGyro(-45, -90);

        robot.sleep(.25);

        robot.lift.setPositionAsync(robot.lift.FIVE_STACK);

        robot.sleep(.05);

        robot.drive.forward().goDistSmooth(1.8 * inchesPerBox, 0.55);

        robot.sleep(.05);

        robot.drive.speed = .3;

        int ITERATIONS = 1;
        for (int i = 0; i < ITERATIONS; i++) {
            robot.lift.setPosition(robot.lift.FIVE_STACK -i *  150);

            //robot.drive.speed = 0.45;

            //robot.drive.forward().goDist(0.125);

            robot.drive.forward().bumperGoDist(0.5 * inchesPerBox, robot.bumpSensorLeft, robot.bumpSensorRight);

            robot.sleep(.05);

            robot.drive.speed = 0.3;

            robot.grabber.close();

            robot.sleep(.3);

            robot.lift.setPosition(robot.lift.SMALL);

            robot.sleep(.05);

            robot.drive.backward().goDist(.5 * inchesPerBox, 0.3);

            robot.sleep(.05);

            robot.drive.rotateRightEncoder(110);

            robot.poleHarmonization(telemetry);

            robot.sleep(.05);

            robot.deliver();

            //robot.drive.rotateLeftEncoder(110);

            robot.drive.rotateGyro(110, -90);
        }

        robot.lift.setPosition(robot.lift.FIVE_STACK - ITERATIONS *  220);

        robot.drive.forward().goDist(.5 * inchesPerBox, 0.3);

        robot.sleep(.05);

        robot.grabber.close();

        robot.sleep(.3);

        robot.lift.setPosition(robot.lift.SMALL);

        robot.sleep(.05);

        robot.drive.speed = .5;

        //robot.drive.speed = .35;

        robot.drive.backward().goDist(0.3 * inchesPerBox, 0.5);

        robot.lift.setPositionAsync(10);

        robot.drive.backward().goDist((3 - id) * inchesPerBox, 0.5);
        robot.sleep(.2);

        robot.lift.setPosition(0);




    }
}
