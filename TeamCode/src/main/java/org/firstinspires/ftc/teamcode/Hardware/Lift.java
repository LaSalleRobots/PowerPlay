package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Lift {
    private DcMotor liftMotor;

    private int TOP_LIMIT = 3010;

    final public int LARGE = TOP_LIMIT;
    final public int MIDDLE = 2120;
    final public int SMALL = 1250;

    final public  int GROUND_JUNCTION = 720;

    //public Supplier<Boolean> isStopRequested;

    final public int FIVE_STACK = 500;
    final public int FOUR_STACK = 400;
    final public int THREE_STACK = 300;
    final public int TWO_STACK = 200;
    final public int ONE_STACK = 100;

    private int delta = 20;

    public Lift(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.get(DcMotor.class, "lift");
        liftMotor.setPower(1);
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //this.isStopRequested = isStopRequested;

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
        this.liftMotor.setTargetPosition(bound(ticks));
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

    public static class Debouncer {
        private boolean lock = false;

        public boolean isPressed(boolean input) {
            if (input) {
                if (!lock) {
                    lock = true;
                    return true;
                }
            } else {
                if (lock) {
                    lock = false;
                }
            }
            return false;
        }

        public void reset() {
            lock = false;
        }
    }
}
