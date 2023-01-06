package org.firstinspires.ftc.teamcode.Util;

/* 2022-2023 FTC Robotics Power-Play
 * (c) 2022-2023 La Salle Robotics
 * Developed for the Power Play competition
 * Written By Lukas Werner ('22)
 */


import com.qualcomm.robotcore.util.ElapsedTime;

// This is a Proportional Derivative Controller
//     It takes a some constants and a target and outputs
//     values to reach that target
public class PIDController {

    double u;  // Force
    double target; // Target thing

    double Kp; // Proportional Gain Constant (Driving force thing)
    double Ki; // Integral Gain Constant (if we get stuck in sticky thing)
    double Kd; // Derivative Gain Constant (damper force thing)

    ElapsedTime timer;
    double lastError;
    double integralSum;

    public PIDController(double Kp, double Ki, double Kd, double target) {
        // Constants for PD control
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;

        // The goal for our PD loop
        this.target = target;

        // Configure surrounding things
        this.timer = new ElapsedTime();
    }

    public double update(double data) {

        double error = this.target - data;

        double derivative = (error - lastError) / timer.seconds();
        lastError = error;

        integralSum = integralSum + (error * timer.seconds());

        this.u = (this.Kp * error)  // Proportional Controller
                + (this.Ki * integralSum) // Integral Controller
                + (this.Kd * derivative); // Derivative Controller

        return this.u;
    }
}
