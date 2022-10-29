package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class VisionCodeTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Vision v = new Vision(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("vision", v.getIdentifier());
            telemetry.update();
        }
    }
}
