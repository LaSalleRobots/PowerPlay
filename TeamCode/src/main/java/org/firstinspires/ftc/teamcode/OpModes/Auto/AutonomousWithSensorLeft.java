package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Vision;

@Autonomous(name="Autonomous With Sensor Left")

@Disabled


public class AutonomousWithSensorLeft extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);
        robot.drive.speed = 0.35;

        final double inchesPerBox = robot.inchesPerBox;
        final double robotLength = robot.robotLength;
        final int directionCoefficient = 1;
        // -1 means right


        int id = 3;

        while (opModeInInit()) {
            id = robot.vision.getIdentifier();
            telemetry.addData("Id:", id);
            telemetry.update();
        }

        if (id == -1) { id = 3; }

        waitForStart();



        robot.grabber.close();
        robot.sleep(0.25);
        robot.lift.setPosition(robot.lift.SMALL);

        robot.drive.forward().goDist((inchesPerBox * .6) - robotLength, 0.5);
        robot.sleep(0.25);

        robot.drive.right().goDist(inchesPerBox * .5 * directionCoefficient, 0.5);
        robot.sleep(0.25);

        robot.drive.forward().goDist(inchesPerBox * .2, 0.5);
        robot.sleep(0.25);

        robot.drive.forward().interruptableGoDist(inchesPerBox * .3, robot.poleSensor);
        robot.sleep(0.25);

        robot.grabber.open();
        robot.sleep(0.25);

        robot.drive.backward().goDist(inchesPerBox * 1, 0.5);
        robot.sleep(0.25);

        // First cone now delivered (we are now in te center of the box)

        robot.drive.right().goDist(inchesPerBox * 0.5 * directionCoefficient, 0.5);
        robot.sleep(0.25);

        //robot.drive.backward().goFor(1);
        //robot.sleep(0.25);

        robot.lift.setPositionAsync(robot.lift.FIVE_STACK);
        robot.drive.forward().variableGoDist(inchesPerBox * 1.9, .35);
        robot.sleep(0.25);

        robot.drive.rotateLeftEncoder(90 * directionCoefficient);
        robot.sleep(0.25);

        robot.drive.forward().goDist(inchesPerBox * 2.2, 0.5);
        
        robot.drive.variableSpeedMode(0.5);
        telemetry.addData("mode", "started");
        telemetry.update();
        robot.drive.forward().goFor(0.75);
        robot.drive.endVariableSpeedMode();

        telemetry.addData("mode", "ended");
        telemetry.update();

        // skeptical may not be needed
        robot.sleep(0.25);

        robot.grabber.close();
        telemetry.addData("mode", "closed");
        telemetry.update();

        robot.sleep(0.25);

        robot.lift.setPosition(robot.lift.SMALL);
        robot.sleep(0.2);
        robot.lift.setPositionAsync(robot.lift.LARGE);

        robot.drive.backward().goDist(inchesPerBox * 1, 0.5);
        robot.sleep(0.25);

        robot.drive.rotateRightEncoder(90 * directionCoefficient);
        robot.sleep(0.25);

        robot.drive.right().goDist((inchesPerBox * .95 - robot.robotDistFront) * directionCoefficient, 0.5);
        robot.sleep(0.25);

        robot.drive.forward().interruptableGoDist(inchesPerBox * .3, robot.poleSensor);
        robot.sleep(0.25);

        robot.lift.setPosition(robot.lift.getPosition() - 220);
        robot.sleep(.25);

        robot.grabber.open();
        robot.sleep(0.25);

        robot.drive.backward().goDist(inchesPerBox * .25, 0.5);
        robot.sleep(0.25);

        robot.lift.setPositionAsync(0);
        robot.sleep(0.1);

        robot.drive.left().goDist(inchesPerBox * (2 + (directionCoefficient/2.0) -(Math.abs(id))), 0.5);
        // this is for left only

    }
}