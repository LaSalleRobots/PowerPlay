package org.firstinspires.ftc.teamcode;

/* 2019-2022 FTC Robotics Freight-Frenzy
 * (c) 2019-2022 La Salle Robotics
 * Developed for the Freight Frenzy competition
 * Written By Lukas Werner ('22)
 */

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Robot {

    private final ElapsedTime runtime;

    public MecanumDrive drive;

	public BNO055IMU imu = null;


    // setup class initializer
    public Robot(HardwareMap hardwareMap, ElapsedTime runtime) {
        this.runtime = runtime;

        // Setup Devices
        this.drive = new MecanumDrive(hardwareMap, runtime);


		// Setup Gyro Sensors
        this.imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        //parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        this.imu.initialize(parameters);
    }

    /*
     * This section is for making the driver programing experience simpler
     */


    // handleGamepads the second gamepad is currently ignored for this input code
    public Robot handleGamepads(Gamepad gamepad1, Gamepad gamepad2) {
        drive.calculateDirections(gamepad1.left_stick_x * 0.75, gamepad1.left_stick_y * 0.75, 0.5*gamepad1.right_stick_x);
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
}