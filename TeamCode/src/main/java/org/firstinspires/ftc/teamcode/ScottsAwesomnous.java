package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Scotts Awesomnous")

public class ScottsAwesomnous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        waitForStart();


        robot.drive.forward().goFor(1);

        robot.drive.sleep(1);

        robot.drive.forward();
        robot.drive.goFor(1);
    }
}
