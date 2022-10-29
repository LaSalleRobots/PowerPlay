package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Grabber {
    public Servo left;
    public Servo right;


    public int maxDegree = 180;
    public int leftClawDegree = 0;
    public int rightClawDegree = 0;
    public boolean state = true;

    public Grabber(HardwareMap hardwareMap) {
        left = hardwareMap.get(Servo.class, "leftGrabber");
        right = hardwareMap.get(Servo.class, "rightGrabber");
    }

    public void open() {
        state = true;
        left.setPosition(30.0/180);
        right.setPosition(150.0/180);
    }

    public void close() {
        state = false;
        left.setPosition(0);
        right.setPosition(1);
    }

    public void toggle() {
        if (state) {
            close();
        } else {
            open();
        }
    }
}
