package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Debouncer {
    private int bounceThreshold = 10;
    private int onSamples = 0;

    public Debouncer(int threshold) {
        bounceThreshold = threshold;
    }

    public Debouncer() {}

    public boolean isPressed(boolean value) {
        if (value) {
            onSamples++;
        }
        if (onSamples >= bounceThreshold) {
            onSamples = 0;
            return true;
        }
        return false;
    }
}