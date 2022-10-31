package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Autonomous High Right")

public class AutonomousHighRight extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        Vision vision = new Vision(hardwareMap);

        while (opModeInInit()) {
            int id = vision.getIdentifier();
            telemetry.addData("Id:", id);
            telemetry.update();
        }

        waitForStart();

        robot.grabber.close();
        robot.sleep(0.5);
        robot.lift.setPosition(80);

        int id = vision.getIdentifier();
        telemetry.addData("Id:", id);
        telemetry.update();

        robot.drive.startSlowMode(.5);
        final double squareSize = 1.25;
        final double sideSquareSize = 2.35;

        robot.drive.left().goFor(sideSquareSize);
        robot.sleep(.5);
        robot.drive.forward().goFor(2.2 * squareSize);
        robot.sleep(.5);
        robot.drive.right().goFor(sideSquareSize/2.0);
        robot.sleep(.5);
        robot.lift.setPosition(robot.lift.LARGE);
        robot.sleep(.5);
        robot.drive.startSlowMode(.25);
        robot.drive.forward().goFor(.3);
        robot.sleep(1);
        robot.grabber.open();
        robot.sleep(.5);
        robot.lift.setPositionAsync(0);
        robot.drive.backward().goFor(.15);
        robot.drive.endSlowMode();
        if (id == 1) {
            robot.drive.left().goFor(sideSquareSize/2.0);
        }
        if (id == 2) {
            robot.drive.right().goFor(sideSquareSize/2.0);
        }
        if (id == 3) {
            robot.drive.right().goFor(sideSquareSize * 1.5);
        }
    }
}
