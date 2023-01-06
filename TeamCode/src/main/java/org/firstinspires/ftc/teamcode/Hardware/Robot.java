package org.firstinspires.ftc.teamcode.Hardware;

/* 2019-2022 FTC Robotics Freight-Frenzy
 * (c) 2019-2022 La Salle Robotics
 * Developed for the Freight Frenzy competition
 * Written By Lukas Werner ('22)
 */

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Robot {

    private final ElapsedTime runtime;

    //public Supplier<Boolean> isStopRequested;
    public MecanumDrive drive;
    public Lift lift;
    public Grabber grabber;
    public TouchSensor bumpSensorLeft, bumpSensorRight;
    public Lift.Debouncer bumbDebouncer = new Lift.Debouncer();
    public Rev2mDistanceSensor poleSensor;

    public double inchesPerBox = 23.3; // 23.3 for meet; 21.5 for school
    public final double robotLength = 11.75;
    final double robotWidth = 15.25;
    public final double robotDistFront  = 8.75;
    final double robotDistBack = 5.75;


	public IMU imu = null;


    // setup class initializer
    public Robot(HardwareMap hardwareMap, ElapsedTime runtime) {
        this.runtime = runtime;


        this.imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters params = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD)
        );
        imu.initialize(params);

        // Setup Devices
        this.drive = new MecanumDrive(hardwareMap, runtime, imu);
        this.lift = new Lift(hardwareMap);
        this.grabber = new Grabber(hardwareMap);
        this.bumpSensorLeft = hardwareMap.get(TouchSensor.class, "bumpLeft");
        this.bumpSensorRight = hardwareMap.get(TouchSensor.class, "bumpRight");
        this.poleSensor = hardwareMap.get(Rev2mDistanceSensor.class, "Pole");
        //this.isStopRequested = isStopRequested;


    }

    /*
     * This section is for making the driver programing experience simpler
     */


    // handleGamepads the second gamepad is currently ignored for this input code
    public Robot handleGamepads(Gamepad gamepad1, Gamepad gamepad2) {
        drive.calculateDirections(gamepad1.left_stick_x, gamepad1.left_stick_y, -0.7 *gamepad1.right_stick_x);
        drive.applyPower();
        return this;
    }

    /*
     * This section is for making autonomous programming simpler
     */

    public void sleep(double sleepTime) {
        double time = runtime.time();
        double initTime = time;

        while (time <= initTime + sleepTime) {
            time = runtime.time();
        }
    }

    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public Orientation getAngles() {
        return imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
    }

    public boolean bumperPressed() {
        return bumbDebouncer.isPressed(bumpSensorLeft.isPressed() && bumpSensorRight.isPressed());
    }

    public Robot deliver(int poleHeight) {
        //drive.recordPosition();
        lift.setPosition(poleHeight);
        drive.interruptableGoDist(inchesPerBox * 0.25, poleSensor);
        sleep(0.5);
        grabber.open();
        //drive.restorePosition();
        grabber.close();
        lift.setPosition(0);

        return this;
    }
}