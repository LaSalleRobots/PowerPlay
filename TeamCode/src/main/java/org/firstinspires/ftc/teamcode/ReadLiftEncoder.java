package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Read Lift Position")
public class ReadLiftEncoder extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Lift lift = new Lift(hardwareMap);
        Grabber grabber = new Grabber(hardwareMap);
        Debouncer db = new Debouncer();
        waitForStart();

        grabber.left.setPosition(0);

        while (opModeIsActive() ) {
            telemetry.addData("ls", grabber.left.getPosition());
            telemetry.addData("rs", grabber.right.getPosition());
            telemetry.addData("Lift Position", lift.getPosition());
            telemetry.update();


            if (gamepad1.dpad_up) {
                lift.up();
            }
            if (gamepad1.dpad_down) {
                lift.down();
            }
            if (db.isPressed(gamepad1.a || gamepad1.cross)) {
                grabber.toggle();

            }
        }
    }
}
