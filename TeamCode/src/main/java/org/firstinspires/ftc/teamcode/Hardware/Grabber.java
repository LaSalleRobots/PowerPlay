package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Grabber {
    public Servo left;
    public Servo right;


    public boolean state = true;

    public Grabber(HardwareMap hardwareMap) {
        left = hardwareMap.get(Servo.class, "leftGrabber");
        right = hardwareMap.get(Servo.class, "rightGrabber");
    }

    public void open() {
        state = true;
        left.setPosition(0);
        right.setPosition(1);
    }

    public void close() {
        state = false;
        left.setPosition(90.0 / 180.0);
        right.setPosition(90.0 / 180.0);
    }

    public void toggle() {
        if (state) {
            close();
        } else {
            open();
        }
    }
}
