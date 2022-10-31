package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Autonomous Right")

public class AutonomousRight extends LinearOpMode {
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

        robot.drive.startSlowMode(.5);

        robot.grabber.close();
        robot.sleep(0.5);
        robot.lift.setPosition(80);

        int id = vision.getIdentifier();

        telemetry.addData("Id:", id);
        telemetry.update();

        //adjust with how long it takes to travel 1 square
        final double SquareSize = 1.25;
        final double sideSquareSize = 2.35;

        if (id == 1) {
            robot.drive.left().goFor(sideSquareSize);
            robot.sleep(.5);
            robot.drive.forward().goFor(2.2 * SquareSize);
        }
        else if (id == 2) {
            robot.drive.left().goFor(sideSquareSize);
            robot.sleep(.5);
            robot.drive.forward().goFor(2.2 * SquareSize);
            robot.sleep(.5);
            robot.drive.right().goFor(sideSquareSize);
        }
        else {
            robot.drive.right().goFor(sideSquareSize);
            robot.sleep(.5);
            robot.drive.forward().goFor(1.3 * SquareSize);
        }

        robot.lift.setPosition(0);
    }
}
