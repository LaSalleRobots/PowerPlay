package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Vision;

@Autonomous
@Disabled
public class AutoEffecientPathLeft extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);
        robot.drive.speed = 0.35;

        final double inchesPerBox = robot.inchesPerBox;
        final double robotLength = robot.robotLength;
        final int directionCoefficient = 1;
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
        robot.lift.setPosition(robot.lift.SMALL);

        robot.drive.forward().goDist(.15 * inchesPerBox, 0.5);
        robot.sleep(.25);

        robot.drive.left().goDist(inchesPerBox/2 * directionCoefficient, 0.5);
        robot.sleep(.25);

        robot.drive.forward().goDist(.85 * inchesPerBox, 0.5);
        robot.sleep(.25);

        robot.drive.forward().interruptableGoDist(inchesPerBox * .3, robot.poleSensor);
        robot.sleep(0.25);

        robot.grabber.open();
        robot.sleep(0.25);

        robot.drive.backward().goDist(inchesPerBox * .25, 0.5);
        robot.sleep(0.25);

        robot.drive.right().goDist(inchesPerBox * .6 * directionCoefficient, 0.5);
        robot.sleep(0.25);

        robot.drive.forward().goDist(.85 * inchesPerBox, 0.5);
        robot.sleep(.25);

        robot.drive.rotateLeftEncoder(90 * directionCoefficient);
        robot.sleep(.25);

        robot.lift.setPositionAsync(robot.lift.FIVE_STACK);

        robot.drive.forward().goDist(1.1 * inchesPerBox + 1.5, 0.5);
        robot.sleep(.25);

        //robot.drive.forward().interruptableGoDist(inchesPerBox * .5, robot.poleSensor);
        //robot.sleep(0.25);
        //robot.drive.goDist(2);
        //robot.sleep(.25);


        robot.grabber.close();
        robot.sleep(.25);
        robot.lift.setPosition(robot.lift.LARGE);

        //robot.sleep(0.2);
        //robot.lift.setPositionAsync(robot.lift.LARGE);

        robot.drive.backward().goDist(inchesPerBox * 1, 0.5);
        robot.sleep(0.25);

        robot.drive.rotateRightEncoder(90 * directionCoefficient);
        robot.sleep(0.25);

        robot.drive.right().goDist((inchesPerBox * 1 - robot.robotDistFront) * directionCoefficient, 0.5);
        robot.sleep(0.25);

        robot.drive.forward().interruptableGoDist(inchesPerBox * .3, robot.poleSensor);
        robot.sleep(0.25);

        robot.lift.setPosition(robot.lift.getPosition() - 220);
        robot.sleep(.25);

        robot.grabber.open();
        robot.sleep(0.25);

        robot.drive.backward().goDist(inchesPerBox * .25, 0.5);
        robot.sleep(0.25);

        robot.lift.setPositionAsync(0);
        robot.sleep(0.1);

        robot.drive.left().goDist(inchesPerBox * (2 + (directionCoefficient/2.0) -(Math.abs(id))), 0.5);





        /*
        while (Math.abs(tmpTarget - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES))) > 2) {
            telemetry.addData("tmpTarget", tmpTarget);
            telemetry.addData("goal:", Math.abs(tmpTarget - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES))));
            telemetry.update();
            robot.drive.calcGyroStabilized(0,0, tmpTarget);
            robot.drive.applyPower();
        }
        */
    }
}
