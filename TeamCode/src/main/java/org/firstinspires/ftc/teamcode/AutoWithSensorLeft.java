package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Autonomous W/ Sensor Left")

public class AutoWithSensorLeft extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        Vision vision = new Vision(hardwareMap);

        final double inchesPerBox = 21.5; // 23.3 for meet
        int id = 3;

        while (opModeInInit()) {
            id = vision.getIdentifier();
            telemetry.addData("Id:", id);
            telemetry.update();
        }

        if (id == -1) { id = 3; }

        waitForStart();

        //robot.grabber.close();
        robot.sleep(0.5);
        robot.lift.setPosition(robot.lift.SMALL);

        robot.drive.forward().goDist(inchesPerBox * .1);
        robot.sleep(0.5);

        robot.drive.right().goDist(inchesPerBox * .5);
        robot.sleep(0.5);

        robot.drive.forward().goDist(inchesPerBox * .25);
        robot.sleep(0.5);

        robot.drive.forward().interruptableGoDist(inchesPerBox * .25, robot.poleSensor);
        robot.sleep(0.5);

        //robot.grabber.open();
        //robot.sleep(0.5);

        robot.drive.backward().goDist(inchesPerBox * .25);
        robot.sleep(0.5);

        robot.drive.left().goDist(inchesPerBox * 1.5);
        robot.sleep(0.5);

        robot.drive.forward().goDist(inchesPerBox * 2);
        robot.sleep(0.5);

        robot.drive.rotateLeftEncoder(90);
        robot.sleep(0.5);

        robot.lift.setPosition(robot.lift.SMALL); // Needs to be adjusted for 5 cones
        robot.sleep(0.5);

        robot.drive.forward().goDist(inchesPerBox * 0.5);
        robot.sleep(0.5);

        //robot.grabber.close();
        //robot.sleep(0.5);

        robot.lift.setPosition(robot.lift.LARGE);
        robot.sleep(0.5);

        robot.drive.backward().goDist(inchesPerBox * 2.1);
        robot.sleep(0.5);

        robot.drive.right().goDist(inchesPerBox * .5);
        robot.sleep(0.5);

        robot.drive.interruptableGoDist(inchesPerBox * .2, robot.poleSensor);
        robot.sleep(0.5);

        //robot.grabber.open();
        //robot.sleep(0.5);

        robot.drive.backward().goDist(inchesPerBox * .1);
        robot.sleep(0.5);

        //robot.grabber.close();
        //robot.sleep(0.5);

        robot.lift.setPosition(0);
        robot.sleep(0.5);

        robot.drive.left().goDist(inchesPerBox * .5);
        robot.sleep(0.5);

        robot.drive.forward().goDist(inchesPerBox * (3 - Math.abs(id)));

    }
}
