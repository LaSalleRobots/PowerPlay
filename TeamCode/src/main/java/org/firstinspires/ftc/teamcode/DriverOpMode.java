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
            if (gamepad1.dpad_up) {
                robot.lift.up();
            } else if (gamepad1.dpad_down) {
                robot.lift.down();
            }
            if (gamepad1.cross) {
                robot.lift.setPositionAsync(robot.lift.LARGE);
            }

            telemetry.addData("Lift", robot.lift.getPosition());
            telemetry.addData("lift target", robot.lift.getTarget());
            telemetry.addData("Heading", robot.getHeading());
            telemetry.update();
        }
    }
}