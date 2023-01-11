package org.firstinspires.ftc.teamcode.OpModes.Testing;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Grabber;
import org.firstinspires.ftc.teamcode.Hardware.Lift;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Vision;

@TeleOp(name = "Read Lift Position")
@Disabled
public class ReadLiftEncoder extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Lift lift = new Lift(hardwareMap);
        Grabber grabber = new Grabber(hardwareMap);
        Lift.Debouncer db = new Lift.Debouncer();
        waitForStart();

        grabber.left.setPosition(0);

        while (opModeIsActive()) {
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