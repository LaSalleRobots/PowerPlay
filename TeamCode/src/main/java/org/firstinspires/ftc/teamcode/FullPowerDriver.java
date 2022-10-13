package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Unlimited Driver-Controlled")
public class FullPowerDriver extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();
        Robot robot = new Robot(hardwareMap, time);


        waitForStart();

        while (opModeIsActive()) {
            robot.drive.calculateDirections(gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x);
            robot.drive.applyPower();
        }
    }
}
