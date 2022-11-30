package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class ReadIMUData extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot(hardwareMap, new ElapsedTime());

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Heading", r.getHeading());
            telemetry.update();
        }
    }
}
