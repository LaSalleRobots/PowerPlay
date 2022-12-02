package org.firstinspires.ftc.teamcode;

/* 2019-2022 FTC Robotics Freight-Frenzy
 * (c) 2019-2022 La Salle Robotics
 * Developed for the Freight Frenzy competition
 * Written By Lukas Werner ('22)
 */

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Robot {

    private final ElapsedTime runtime;

    public MecanumDrive drive;
    public Lift lift;
    public Grabber grabber;
    public TouchSensor bumpSensorLeft, bumpSensorRight;
    public Debouncer bumbDebouncer = new Debouncer();
    public Rev2mDistanceSensor poleSensor;


	public BNO055IMU imu = null;


    // setup class initializer
    public Robot(HardwareMap hardwareMap, ElapsedTime runtime) {
        this.runtime = runtime;

        // Setup Devices
        this.drive = new MecanumDrive(hardwareMap, runtime);
        this.lift = new Lift(hardwareMap);
        this.grabber = new Grabber(hardwareMap);
        this.bumpSensorLeft = hardwareMap.get(TouchSensor.class, "bumpLeft");
        this.bumpSensorRight = hardwareMap.get(TouchSensor.class, "bumpRight");
        this.poleSensor = hardwareMap.get(Rev2mDistanceSensor.class, "Pole");



		// Setup Gyro Sensors
        this.imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.mode = BNO055IMU.SensorMode.IMU;
        //parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        this.imu.initialize(parameters);

        while (!imu.isGyroCalibrated()) { }
        BNO055IMUUtil.remapZAxis(imu, AxisDirection.NEG_Y);
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
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
    }

    public Orientation getAngles() {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
    }

    public boolean bumperPressed() {
        return bumbDebouncer.isPressed(bumpSensorLeft.isPressed() && bumpSensorRight.isPressed());
    }

    public Robot rotateToDegree(double degree) {

        return this;
    }
}