package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Vision;

@Autonomous
public class PoleSensingAutoRight extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        //robot.drive.speed = 0.35;

        robot.drive.speed = 0.65;

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

        robot.lift.setPosition(10);

        robot.drive.forward().goDistSmooth(2.1 * inchesPerBox);


//        robot.drive.rotateGyro(45, 45);

        robot.drive.rotateLeftEncoder(45);

        robot.drive.speed = 0.35;

        robot.poleHarmonization(telemetry);

        robot.drive.speed = 0.35;

        robot.lift.setPosition(robot.lift.LARGE);


        robot.drive.forward().goDist(.2 * inchesPerBox);
        robot.deliver();

        robot.sleep(1);
       // robot.drive.rotateGyro(-45, 0);
       // robot.sleep(1);
        robot.drive.rotateGyro(-135, -90);

        robot.drive.speed = 0.50;

        robot.lift.setPosition(robot.lift.FIVE_STACK);
        robot.sleep(.2);

        robot.drive.forward().goDistSmooth(1.3 * inchesPerBox);
        robot.sleep(0.25);

        robot.grabber.close();
        robot.sleep(.25);

        robot.lift.setPosition(robot.lift.LARGE);

        //robot.sleep(0.2);
        //robot.lift.setPositionAsync(robot.lift.LARGE);

        robot.drive.backward().goDist(inchesPerBox * 1);
        robot.sleep(0.1);

//        robot.drive.rotateRightEncoder(90 * directionCoefficient);
        robot.drive.rotateGyro(90, 0);
        robot.sleep(0.25);

        robot.drive.right().goDist((inchesPerBox * 1 - robot.robotDistFront) * directionCoefficient);
        robot.sleep(0.25);

        robot.drive.forward().interruptableGoDist(inchesPerBox * .3, robot.poleSensor);
        robot.sleep(0.25);

        robot.lift.setPosition(robot.lift.getPosition() - 220);
        robot.sleep(.25);

        robot.grabber.open();
        robot.sleep(0.25);

        robot.drive.backward().goDist(inchesPerBox * .25);
        robot.sleep(0.25);

        robot.lift.setPositionAsync(0);
        robot.sleep(0.1);

        robot.drive.left().goDist(inchesPerBox * (2 + (directionCoefficient/2.0) -(Math.abs(id))));


        //robot.drive.interruptableGoDist(0.5 * inchesPerBox, robot.poleSensor);
    }
}
