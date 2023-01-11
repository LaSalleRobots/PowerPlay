package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Vision;

@Autonomous(name = "AutoEfficientRightGyro")
public class AutoEffeciantPathRightGyro extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);
        robot.drive.speed = 0.35;

        Vision vision = new Vision(hardwareMap);

        final double inchesPerBox = robot.inchesPerBox;
        final double robotLength = robot.robotLength;
        final int directionCoefficient = -1;
        // -1 means right

        int id = 3;

        while (opModeInInit()) {
            id = vision.getIdentifier();
            telemetry.addData("Id:", id);
            telemetry.addData("pos:", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }

        if (id == -1) { id = 3; }

        waitForStart();

        robot.imu.resetYaw();
        telemetry.addData("pos:", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        telemetry.update();

        robot.grabber.close();
        robot.sleep(0.2);
        robot.lift.setPosition(robot.lift.SMALL);

        robot.drive.forward().goDist(.15 * inchesPerBox);
        robot.sleep(0.2);

        robot.drive.left().goDist(inchesPerBox/2 * directionCoefficient);
        robot.sleep(0.2);

        robot.drive.forward().goDist(.85 * inchesPerBox);
        robot.sleep(0.2);

        robot.drive.forward().interruptableGoDist(inchesPerBox * .3, robot.poleSensor);
        robot.sleep(0.2);

        robot.drive.backward().goDist(0.05 * inchesPerBox);
        robot.sleep(0.2);

        robot.grabber.open();
        robot.sleep(0.2);

        robot.drive.backward().goDist(inchesPerBox * .2);
        robot.sleep(0.2);

        robot.drive.right().goDist(inchesPerBox * .6 * directionCoefficient);
        robot.sleep(0.2);

        robot.drive.forward().goDist(1 * inchesPerBox);
        robot.sleep(0.2);

        robot.drive.rotateGyro(90 * directionCoefficient, 90 * directionCoefficient);
        robot.sleep(0.2);

        robot.lift.setPositionAsync(robot.lift.FIVE_STACK - 60);

        robot.drive.forward().goDist(1.1 * inchesPerBox + 1.5);
        robot.sleep(0.2);

        //robot.drive.forward().interruptableGoDist(inchesPerBox * .5, robot.poleSensor);
        //robot.sleep(0.2));
        //robot.drive.goDist(2);
        //robot.sleep(0.2));


        robot.grabber.close();
        robot.sleep(0.2);
        robot.lift.setPosition(robot.lift.LARGE);

        //robot.sleep(0.2);
        //robot.lift.setPositionAsync(robot.lift.LARGE);

        robot.drive.backward().goDist(inchesPerBox * 1);
        robot.sleep(0.2);

        robot.drive.rotateGyro(-90 * directionCoefficient, 0);
        robot.sleep(0.2);

        robot.drive.right().goDist((inchesPerBox * .95 - robot.robotDistFront) * directionCoefficient);
        robot.sleep(0.2);

        robot.drive.forward().interruptableGoDist(inchesPerBox * .3, robot.poleSensor);
        robot.sleep(0.2);

        robot.lift.setPosition(robot.lift.getPosition() - 220);
        robot.sleep(0.2);

        robot.grabber.open();
        robot.sleep(0.2);

        robot.drive.backward().goDist(inchesPerBox * .25);
        robot.sleep(0.2);

        robot.lift.setPositionAsync(0);
        robot.sleep(0.1);

        robot.drive.left().goDist(inchesPerBox * (2 + (directionCoefficient/2.0) -(Math.abs(id))));





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
