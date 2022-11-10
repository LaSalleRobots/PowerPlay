package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class AST extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();
        Robot robot = new Robot(hardwareMap, time);

        telemetry.addData("Front Right", robot.drive.rightFront.getCurrentPosition());
        telemetry.addData("Front Left", robot.drive.leftFront.getCurrentPosition());
        telemetry.addData("Back Right", robot.drive.rightBack.getCurrentPosition());
        telemetry.addData("Back Left", robot.drive.leftBack.getCurrentPosition());
        telemetry.update();
        waitForStart();


        //robot.drive.runToPosition(2719, 2719, 2719, 2719);
        robot.drive.startSlowMode(0.25);
        robot.drive.forward().goDist(30);
        //int x = (int)(40.88721* 30);
        //robot.drive.runToPosition(x,x,x,x);
        //robot.drive.off();


        telemetry.addData("planned target", robot.drive.TICKS_PER_INCH * 30);
        telemetry.addData("Front Right", robot.drive.rightFront.getCurrentPosition());
        telemetry.addData("Front Left", robot.drive.leftFront.getCurrentPosition());
        telemetry.addData("Back Right", robot.drive.rightBack.getCurrentPosition());
        telemetry.addData("Back Left", robot.drive.leftBack.getCurrentPosition());
        telemetry.update();

        while (opModeIsActive()) {}
    }
}
