package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Vision;

@Autonomous(group = "Testing")
public class AutoSmallSpamTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        robot.drive.speed = 0.35;

        final double inchesPerBox = robot.inchesPerBox;
        final double robotLength = robot.robotLength;
        final int directionCoefficient = -1;
        // -1 means right

        int id = 3;

        while (opModeInInit()) {
            id = robot.vision.getIdentifier();
            telemetry.addData("Id:", id);
            telemetry.update();
        }

        if (id == -1) { id = 3; }

        id = 3;

        robot.vision.switchToPolesMode();
        waitForStart();

        robot.imu.resetYaw();

        robot.drive.rotateGyro(-90, -90);

        int ITERATIONS = 2;
        for (int i = 0; i < ITERATIONS; i++) {

            robot.lift.setPosition(robot.lift.FIVE_STACK - (i * 100));
            robot.sleep(0.25);

            robot.drive.forward().goDistSmooth(1.25 * inchesPerBox);
            robot.sleep(0.25);

            robot.grabber.close();

            robot.lift.setPosition(robot.lift.SMALL);
            robot.sleep(0.25);

            robot.drive.backward().goDistSmooth(inchesPerBox * 1.25);
            robot.sleep(0.25);

            robot.drive.rotateGyro(-45, -135);
            robot.sleep(0.25);

            robot.drive.forward().goDist(0.2 * inchesPerBox);
            robot.sleep(0.25);

            robot.deliver();

            robot.drive.backward().goDist(0.2 * inchesPerBox);
            robot.sleep(0.25);

            robot.drive.rotateGyro(45, -90);
            robot.sleep(0.25);

        }

    }
}
