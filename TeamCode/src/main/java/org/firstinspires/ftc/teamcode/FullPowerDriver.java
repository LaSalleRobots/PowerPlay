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
        double x;
        double y;
        double r;

        waitForStart();

        while (opModeIsActive()) {
            //prematurely combines joystick values, this is purely organizational
            x = ((0.75 * gamepad1.left_stick_x) + (0.25 * gamepad2.left_stick_x));
            y = ((0.75 * gamepad1.left_stick_y) + (0.25 * gamepad2.left_stick_y));
            r = ((0.25 * gamepad1.right_stick_x) + (0.05 * gamepad2.right_stick_x));
            //applies values. Notice the negative R.
            robot.drive.calculateDirections(x, y, -r);
            robot.drive.applyPower();
        }
    }
}
