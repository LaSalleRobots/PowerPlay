package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Vision;

@Autonomous
public class PoleSensingAutoRight extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);
        robot.vision.switchToPolesMode();
        robot.drive.speed = 0.35;

        robot.drive.variableSpeedMode(0.65);

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

        waitForStart();

        robot.grabber.close();
        robot.sleep(0.25);

        robot.lift.setPosition(robot.lift.LARGE);

        robot.drive.forward().goDist(2.1 * inchesPerBox);


//        robot.drive.rotateGyro(45, 45);

        robot.drive.rotateLeftEncoder(45);

        robot.poleHarmonization(telemetry);
        robot.drive.interruptableGoDist(0.4 * inchesPerBox, robot.poleSensor);

        robot.grabber.open();
        robot.drive.backward().goDist(.5 * inchesPerBox);
        //robot.drive.interruptableGoDist(0.5 * inchesPerBox, robot.poleSensor);
    }
}
