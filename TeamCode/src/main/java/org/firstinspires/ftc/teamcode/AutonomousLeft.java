package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Autonomous Left")

public class AutonomousLeft extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        Vision vision = new Vision(hardwareMap);

        int id = vision.getIdentifier();

        waitForStart();

        robot.drive.startSlowMode(.75);
        final double squareSize = 2;
        final double sideSquareSize = squareSize * 1;
        if (id == 1) {
            robot.drive.right().goFor(sideSquareSize);
            robot.drive.forward().goFor(2 * squareSize);
        }

        if (id == 2) {
            robot.drive.left().goFor(sideSquareSize);
            robot.drive.forward().goFor(2 * squareSize);
            robot.drive.right().goFor(sideSquareSize);
        }

        if (id == 3) {
            robot.drive.left().goFor(sideSquareSize);
            robot.drive.forward().goFor(squareSize);
        }


    }
}
