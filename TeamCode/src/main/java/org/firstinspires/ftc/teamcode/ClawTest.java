package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Unlimited claw test")

public class ClawTest extends LinearOpMode {
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

        waitForStart();
    }
}