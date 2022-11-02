package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Autonomous W/ Encoders Left")

public class AutoWithEncodersLeft extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        Vision vision = new Vision(hardwareMap);

        while (opModeInInit()) {
            int id = vision.getIdentifier();
            telemetry.addData("Id:", id);
            telemetry.update();
        }

        waitForStart();

        robot.grabber.close();
        robot.sleep(0.5);
        robot.lift.setPosition(80);

        int id = vision.getIdentifier();
        telemetry.addData("Id:", id);
        telemetry.update();

        robot.lift.setPosition(125);

        robot.drive.runToPosition(98, 97, 106, 105)
                .runToPosition(999, 945, -863, -886);
        robot.lift.setPosition(3010);
        robot.sleep(0.5);
        robot.drive.runToPosition(2612, 2601, 788, 801)
                .runToPosition(2873, 2347, 1099, 577);

        robot.drive.runToPosition(3297, 2750, 1523, 986);
        robot.grabber.open();
        robot.sleep(.5);
        robot.lift.setPosition(125);


    }
}
