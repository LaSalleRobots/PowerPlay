package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Read Lift Position")
public class ReadLiftEncoder extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Lift lift = new Lift(hardwareMap);

        while (opModeIsActive() ) {
            telemetry.addData("Lift Position", lift.getPosition());
            telemetry.update();
        }
    }
}
