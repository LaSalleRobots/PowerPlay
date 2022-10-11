package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "DriverControlled")

public class DriverOpMode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        waitForStart();

        while (opModeIsActive()) {
            robot.handleGamepads(gamepad1, gamepad2);

        }
    }
}