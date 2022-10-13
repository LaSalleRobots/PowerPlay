package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {
    private DcMotor liftMotor;

    private int TOP_LIMIT = 1440 * 5;

    public int LARGE = TOP_LIMIT;
    public int MIDDLE = 1440 * 3;
    public int SMALL = 1440 * 2;
    public  int GROUND_JUNCTION = 1440/2;

    private int delta = 50;

    public Lift(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.get(DcMotor.class, "lift");
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }


    public Lift setPosition(int ticks) {
        this.setPositionAsync(ticks);
        while (this.liftMotor.isBusy()) {}
        return this;
    }

    public Lift setPositionAsync(int ticks) {
        int limited = bound(ticks);
        this.liftMotor.setTargetPosition(delta);
        return this;
    }

    public Lift up() {
        int currentPosition = this.liftMotor.getTargetPosition();
        setPositionAsync(currentPosition+delta);
        return this;
    }

    public Lift down() {
        int currentPosition = this.liftMotor.getTargetPosition();
        setPositionAsync(currentPosition-delta);
        return this;
    }

    public Lift preset(int ticks) {
        setPositionAsync(ticks);
        return this;
    }

    public int bound(int ticks) {
        if (ticks > TOP_LIMIT) {
            return TOP_LIMIT;
        } else if (ticks < 0) {
            return 0;
        }
        return ticks;
    }
}
