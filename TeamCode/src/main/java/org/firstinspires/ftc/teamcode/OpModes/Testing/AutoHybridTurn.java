package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.Vision;

@Autonomous(name = "HybridTurn")
@Disabled
public class AutoHybridTurn extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();

        Robot robot = new Robot(hardwareMap, time);
        robot.drive.speed = 0.35;

        waitForStart();
        robot.imu.resetYaw();

        robot.drive.rotateGyro(-90,-90);
        //int heading = -90;
/*
        robot.drive.rotateLeftEncoder(heading);
        //robot.sleep(.25);

        while(Math.abs(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - heading) >= 0.5){
            telemetry.addData("pos:", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.update();
            if(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) < heading){
                robot.drive.calculateDirections(0,0,-0.4);
                robot.drive.applyPower();
            }
            else {
                if(!(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > heading)) {
                    robot.drive.calculateDirections(0,0,0);
                    robot.drive.applyPower();
                }
            if(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > heading){
                robot.drive.calculateDirections(0,0,0.4);
                robot.drive.applyPower();
            }
            else {
                if(!(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) < heading)) {
                    robot.drive.calculateDirections(0,0,0);
                    robot.drive.applyPower();
                    }
                }
            }
        }*/
    }
}


