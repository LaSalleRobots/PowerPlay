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

        int id = vision.getIdentifier();

        waitForStart();

        if (id == 1) {
            robot.drive.left().goFor(1);
            robot.drive.forward();
        }




    }
}
