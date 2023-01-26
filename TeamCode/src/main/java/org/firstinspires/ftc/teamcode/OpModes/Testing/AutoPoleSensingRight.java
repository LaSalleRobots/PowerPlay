package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Vision;

@Autonomous(group = "Testing")
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

        robot.drive.forward().goDist(.1);

        robot.sleep(.05);

        robot.drive.left().goDist(1 * inchesPerBox);

        robot.sleep(.25);

        robot.lift.setPositionAsync(robot.lift.LARGE);

        robot.drive.speed = 0.75;

        robot.drive.forward().goDistSmooth(2.15 * inchesPerBox);

        robot.sleep(.25);

        robot.drive.speed = .35;

        robot.drive.rotateRightEncoder(45);

        robot.drive.speed = .45;

        robot.drive.forward().goDist(.15 * inchesPerBox);

        robot.sleep(0.05);

        robot.poleHarmonization(telemetry);

        double p = 0.35;
        robot.drive.leftFront.setPower(p);
        robot.drive.rightFront.setPower(p);
        robot.drive.leftBack.setPower(p);
        robot.drive.rightBack.setPower(p);

        robot.sleep(.05);

        robot.deliver();

        robot.sleep(.05);

        robot.drive.backward().goDist(.3 * inchesPerBox);

        robot.sleep(.05);
        robot.drive.speed = .55;

        robot.drive.rotateGyro(-45, -90);

        robot.sleep(.2);

        robot.lift.setPositionAsync(robot.lift.FIVE_STACK);

        robot.sleep(.05);

        robot.drive.forward().goDist(1.8 * inchesPerBox);

        robot.sleep(.05);

        robot.drive.speed = .3;

        int ITERATIONS = 1;
        for (int i = 0; i < ITERATIONS; i++) {
            robot.lift.setPosition(robot.lift.FIVE_STACK -i *  150);

            robot.drive.forward().bumperGoDist(0.5 * inchesPerBox, robot.bumpSensorLeft, robot.bumpSensorRight);

            robot.sleep(.05);

            robot.grabber.close();

            robot.sleep(.3);

            robot.lift.setPosition(robot.lift.SMALL);

            robot.sleep(.05);

            robot.drive.backward().goDist(.5 * inchesPerBox);

            robot.sleep(.05);

            robot.drive.rotateRightEncoder(110);

            robot.poleHarmonization(telemetry);

            robot.sleep(.05);

            robot.deliver();

            //robot.drive.rotateLeftEncoder(110);

            robot.drive.rotateGyro(110, -90);
        }

        robot.lift.setPosition(robot.lift.FIVE_STACK -ITERATIONS *  150);

        robot.drive.forward().goDist(.5 * inchesPerBox);

        robot.sleep(.05);

        robot.grabber.close();

        robot.sleep(.3);

        robot.lift.setPosition(robot.lift.SMALL);

        robot.sleep(.05);

        robot.drive.speed = .55;

        if(id == 3){

            robot.drive.speed = .35;

            robot.drive.backward().goDist(.5 * inchesPerBox);

            robot.sleep(.05);

            robot.drive.rotateRightEncoder(110);

            //robot.poleHarmonization(telemetry);

            robot.sleep(.05);

            robot.deliver();

            //robot.drive.rotateLeftEncoder(110);

            robot.drive.rotateGyro(110, -90);

            robot.drive.forward().goDist(.5 * inchesPerBox);

        }
        else{
            robot.drive.backward().goDist((3 - id) * inchesPerBox);
            robot.sleep(.2);

            robot.lift.setPosition(0);
        }



    }
}
