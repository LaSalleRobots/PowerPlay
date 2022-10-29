package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class GrabberTester extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Grabber g = new Grabber(hardwareMap);
        Debouncer db = new Debouncer();

        waitForStart();

        while (opModeIsActive()) {

            if (db.isPressed(gamepad1.cross)) {
                g.toggle();
            }


        }

    }
}
