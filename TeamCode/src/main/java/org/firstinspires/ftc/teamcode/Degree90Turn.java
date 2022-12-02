package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="90 Degree Turn")

public class Degree90Turn extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        while (opModeInInit()) {
            double sense = robot.poleSensor.getDistance(DistanceUnit.CM);
            telemetry.addData("Distance:", sense);
            telemetry.update();
        }

        waitForStart();

        robot.drive.forward().interruptableGoDist(14, robot.poleSensor);
        robot.sleep(0.5);

    }
}