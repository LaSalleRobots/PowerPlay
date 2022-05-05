package org.firstinspires.ftc.teamcode;

class Math {
    public static double magnitude(double x, double y) {
        return -Math.hypot(x, y);
    }

    public static double angle(double x, double y) {
        return Math.atan2(y, x);
    }
}