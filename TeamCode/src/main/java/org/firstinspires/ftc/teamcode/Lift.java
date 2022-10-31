package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {
    private DcMotor liftMotor;

    private int TOP_LIMIT = 3060- 50;

    public int LARGE = TOP_LIMIT;
    public int MIDDLE = 2220 - 50;
    public int SMALL = 1411 - 50;
    public  int GROUND_JUNCTION = 1440/2;

    private int delta = 20;

    public Lift(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.get(DcMotor.class, "lift");
        liftMotor.setPower(1);
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public boolean isActive() {return liftMotor.isBusy();}

    public int getTarget() {
        return liftMotor.getTargetPosition();
    }
    public int getPosition(){
        return liftMotor.getCurrentPosition();
    }

    public Lift setPosition(int ticks) {
        this.setPositionAsync(ticks);
        while (this.liftMotor.isBusy()) {}
        return this;
    }

    public Lift setPositionAsync(int ticks) {
        int limited = bound(ticks);
        this.liftMotor.setTargetPosition(limited);
        this.liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
