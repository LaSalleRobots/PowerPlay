package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous()
public class AutoEncoderTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);

        Vision vision = new Vision(hardwareMap);

        double ticksPerIn = 40.887;

        int inchesPerBox = 24;


        while (opModeInInit()) {
            int id = vision.getIdentifier();
            telemetry.addData("Id:", id);
            telemetry.update();
        }

        waitForStart();

        // Go dist is in inches
        robot.drive.forward().goDist(2 * ticksPerIn);
        robot.sleep(0.5);

        robot.drive.right().goDist(inchesPerBox * ticksPerIn);
        robot.sleep(0.5);

        robot.drive.forward().goDist(2 * inchesPerBox * ticksPerIn);
        robot.sleep(0.5);

        robot.drive.left().goDist(0.5 * inchesPerBox * ticksPerIn);
        robot.sleep(0.5);

        robot.grabber.open();
        robot.sleep(.5);
        robot.lift.setPosition(125);

    }

}
