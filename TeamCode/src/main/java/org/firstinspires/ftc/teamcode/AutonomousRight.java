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


        waitForStart();

        int id = vision.getIdentifier();

        telemetry.addData("read pos", id);
        telemetry.update();


        robot.drive.startSlowMode(.75);

        //adjust with how long it takes to travel 1 square
        final double SquareSize = 2;
        final double sideSquareSize = 2.5;

        if (id == 1) {
            robot.drive.left().goFor(2 * sideSquareSize);
            robot.drive.forward().goFor(SquareSize);
        }
        else if (id == 2) {
            robot.drive.right().goFor(sideSquareSize);
            robot.drive.forward().goFor(2 * SquareSize);
            robot.drive.left().goFor(sideSquareSize);
        }
        else {
            robot.drive.right().goFor(sideSquareSize);
            robot.drive.forward().goFor(2 * SquareSize);
        }
    }
}
