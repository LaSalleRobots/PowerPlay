package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Autonomous W/ Sensor")

public class AutoWithSensor extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        Vision vision = new Vision(hardwareMap);

        final double inchesPerBox = robot.inchesPerBox;
        final double robotLength = robot.robotLength;
        final int directionCoefficient = 1;

        int id = 3;

        while (opModeInInit()) {
            id = vision.getIdentifier();
            telemetry.addData("Id:", id);
            telemetry.update();
        }

        if (id == -1) { id = 3; }

        waitForStart();

        robot.grabber.close();
        robot.sleep(0.25);
        robot.lift.setPosition(robot.lift.SMALL);

        robot.drive.forward().goDist((inchesPerBox * .6) - robotLength);
        robot.sleep(0.25);

        robot.drive.right().goDist(inchesPerBox * .5 * directionCoefficient);
        robot.sleep(0.25);

        robot.drive.forward().goDist(inchesPerBox * .25);
        robot.sleep(0.25);

        robot.drive.forward().interruptableGoDist(inchesPerBox * .25, robot.poleSensor);
        robot.sleep(0.25);

        robot.grabber.open();
        robot.sleep(0.25);

        robot.drive.backward().goDist(inchesPerBox * .25);
        robot.sleep(0.25);

        // First cone now delivered (we are now in te center of the box)

        robot.drive.right().goDist(inchesPerBox * 0.5 * directionCoefficient);
        robot.sleep(0.25);

        robot.drive.backward().goFor(1);
        robot.sleep(0.25);

        robot.lift.setPositionAsync(robot.lift.FIVE_STACK);
        robot.drive.forward().goDist(inchesPerBox * 2.5 - robotLength * 0.75);
        robot.sleep(0.25);

        robot.drive.rotateLeftEncoder(90 * directionCoefficient);
        robot.sleep(0.25);


        robot.drive.forward().goDist(inchesPerBox * 2.2);
        robot.drive.startSlowMode(0.5);
        telemetry.addData("mode", "started");
        telemetry.update();
        robot.drive.forward().goFor(0.75);
        robot.drive.endSlowMode();
        telemetry.addData("mode", "ended");
        telemetry.update();

        // skeptical may not be needed
        robot.sleep(0.25);

        robot.grabber.close();
        telemetry.addData("mode", "closed");
        telemetry.update();

        robot.sleep(0.25);

        robot.lift.setPosition(robot.lift.LARGE);
        robot.sleep(0.1);

        robot.drive.backward().goDist(inchesPerBox * 1.5);
        robot.sleep(0.25);

        robot.drive.backward().variableGoDist(inchesPerBox * .6, .1);
        robot.sleep(.25);

        robot.drive.right().goDist(inchesPerBox * .4 * directionCoefficient);
        robot.sleep(0.25);

        robot.drive.forward().interruptableGoDist(inchesPerBox * .4, robot.poleSensor);
        robot.sleep(0.25);

        robot.grabber.open();
        robot.sleep(0.25);

        robot.drive.backward().goDist(inchesPerBox * .25);
        robot.sleep(0.25);

        robot.drive.left().goDist(inchesPerBox * .4 * directionCoefficient);
        robot.sleep(0.25);

        robot.lift.setPosition(0);
        robot.sleep(0.1);

        robot.drive.forward().goDist(inchesPerBox * (3 - Math.abs(id)));


    }
}