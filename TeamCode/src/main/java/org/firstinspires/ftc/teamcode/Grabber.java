package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Grabber {
    private Servo left;
    private Servo right;

    Servo thing;

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
        left.setPosition(leftClawDegree/maxDegree);
        right.setPosition(rightClawDegree/maxDegree);
    }

    public void close() {
        state = false;
        left.setPosition(1);
        right.setPosition(0);
    }

    public void toggle() {
        if (state) {
            close();
        } else {
            open();
        }
    }
}
