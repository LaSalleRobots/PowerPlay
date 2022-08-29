package org.firstinspires.ftc.teamcode;

/* 2022-2023 FTC Robotics Power-Play
 * (c) 2022-2023 La Salle Robotics
 * Developed for the Power Play competition
 * Written By Lukas Werner ('22)
 */


// This is a Proportional Derivative Controller
//     It takes a some constants and a target and outputs
//     values to reach that target
public class PDController {

    double u;  // Force
    double target; // Target thing

    double Kp; // Proportional Gain Constant (Driving force thing)
    double Kd; // Derivative Gain Constant (damper force thing)

    public PDController(double Kp, double Kd, double target) {
        // Constants for PD control
        this.Kp = Kp;
        this.Kd = Kd;

        // The goal for our PD loop
        this.target = target;
    }

    public double update(double data, double derivative) {

        double error = this.target - data;

        this.u = (this.Kp * error)  // Proportional Controller
                + (this.Kd * derivative); // Derivative Controller

        return this.u;
    }
}
