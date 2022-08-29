package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Debouncer {
    private ElapsedTime time = new ElapsedTime();
    private double bounceThreshold = 0.1;

    public Debouncer(double threshold) {
        bounceThreshold = threshold;
        time = new ElapsedTime();
    }

    public Debouncer() {
        time = new ElapsedTime();
    }

    public boolean isPressed(boolean value) {
        if (time.seconds() > bounceThreshold) {
            time.reset();
            return value;
        }
        return false;
    }
}