package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Autonomous W/ Encoders Right Test")

public class AutoWithEncodersRightTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        Vision vision = new Vision(hardwareMap);

        final double inchesPerBox = 21.5; // 23.3 for meet
        int id = 3;

        while (opModeInInit()) {
            id = vision.getIdentifier();
            telemetry.addData("Id:", id);
            telemetry.update();
        }

        if (id == -1) { id = 3; }

        waitForStart();




        robot.grabber.close();
        robot.sleep(0.5);
        robot.lift.setPosition(robot.lift.SMALL);

        robot.drive.forward().goDist(1);
        robot.sleep(0.5);

        robot.drive.left().goDist(inchesPerBox);
        robot.sleep(0.5);

        robot.drive.forward().goDist(2 * inchesPerBox);
        robot.sleep(0.5);

        robot.drive.right().goDist(0.5 * inchesPerBox);
        robot.sleep(0.5);

        robot.lift.setPosition(robot.lift.LARGE);
        robot.sleep(0.5);

        robot.drive.forward().goDist(4.25);
        robot.sleep(0.5);

        robot.grabber.open();
        robot.sleep(0.5);

        robot.drive.backward().goDist(4.25);
        robot.sleep(0.5);

        robot.lift.setPosition(0);
        robot.sleep(0.5);

        robot.drive.rotateRightEncoder90();
        robot.sleep(0.5);
        //robot.lift.setPosition(robot.lift.getTarget() + 50);
        //robot.sleep(0.5);
        //robot.drive.forward().goDist(1.8 * inchesPerBox);
        //robot.sleep(0.5);
        //robot.grabber.close();
        //robot.sleep(0.5);
        //robot.lift.setPosition(robot.lift.SMALL);
        //robot.sleep(0.5);
        //robot.drive.backward().goDist(5.0);



        /*if (id == 1) {
            robot.drive.rotateLeftEncoder90();
            robot.sleep(0.5);
            robot.drive.rotateLeftEncoder90();
            robot.sleep(0.5);
            robot.drive.rotateLeftEncoder45();
            robot.sleep(0.5);
            robot.lift.setPosition(robot.lift.SMALL);
            robot.sleep(0.5);
            robot.grabber.open();
            robot.drive.rotateRightEncoder45();
            robot.drive.backward().goDist(1.5 * inchesPerBox);
            robot.sleep(0.5);
        }
        if (id == 2) {
            robot.drive.rotateLeftEncoder90();
            robot.sleep(0.5);
            robot.drive.rotateLeftEncoder90();
            robot.sleep(0.5);
            robot.drive.rotateLeftEncoder45();
            robot.sleep(0.5);
            robot.lift.setPosition(robot.lift.SMALL);
            robot.sleep(0.5);
            robot.grabber.open();
            robot.drive.rotateRightEncoder45();
            robot.drive.forward().goDist(0.5 * inchesPerBox);
            robot.sleep(0.5);
        }
        if (id == 3) {
            robot.drive.rotateLeftEncoder90();
            robot.sleep(0.5);
            robot.drive.rotateLeftEncoder90();
            robot.sleep(0.5);
            robot.drive.rotateLeftEncoder45();
            robot.sleep(0.5);
            robot.lift.setPosition(robot.lift.SMALL);
            robot.sleep(0.5);
            robot.grabber.open();

        } */
    }
}
