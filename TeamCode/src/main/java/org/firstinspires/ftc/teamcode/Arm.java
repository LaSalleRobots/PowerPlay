package org.firstinspires.ftc.teamcode;

/* 2019-2022 FTC Robotics Freight-Frenzy
 * (c) 2019-2022 La Salle Robotics
 * Developed for the Freight Frenzy competition
 * Written By Lukas Werner ('22)
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {

    public double GROUND_LEVEL = 3; // degrees for the ground
    // Fixed degrees for where the arm should be for each level of placement
    public double BOTTOM_LEVEL = 25;
    public double MIDDLE_LEVEL = 52;
    public double TOP_LEVEL = 80;

    public Gripper gripper;

    public final Servo wristRight;
    public final Servo wristLeft;
    public final DcMotor arm;
    public Debouncer wristServoDeb = new Debouncer(.0166);

    // Internal State
    private boolean clawOpen;
    public double armPosition = 0;
    public double armDelta = 0.5; // how much the arm should raise/lower by when asking to do those ops
    public int tickAccuracy = 20;

    public Arm (HardwareMap hardwareMap) {
        this.arm = hardwareMap.get(DcMotor.class, "arm");
        this.arm.setPower(0.7);


        // Setup Servos
        this.wristRight = hardwareMap.get(Servo.class, "wristRight");
        this.wristLeft = hardwareMap.get(Servo.class, "wristLeft");

        // Setup Encoders
        this.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.gripper = new Gripper(hardwareMap);

    }

    public void setPositionAsyncUnbounded(double degrees) {
        this.armPosition = degrees;
        this.arm.setTargetPosition(convertDegreesToTicks(this.armPosition));
        this.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.updateWrist();
    }

    public void setPositionAsync(double degrees) {
        this.armPosition = bound(degrees);
        this.arm.setTargetPosition(convertDegreesToTicks(this.armPosition));
        this.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.updateWrist();
    }

    public void setPosition(double degrees) {
        // this is the same as the async version except it waits until the arm has gotten within the tick accuracy
        this.setPositionAsync(degrees);
        while (this.arm.isBusy()) {}
    }

    public int convertDegreesToTicks(double degrees) {
        return (int)(degrees * 12);
    }

    public void updateWrist() {
        if (wristServoDeb.isPressed(true)) {
            if (this.armPosition > 130) {
                this.wristRight.setPosition(20.0/180);
                this.wristLeft.setPosition(160.0/180);
            } else {
                this.wristRight.setPosition((this.armPosition-20)/180);
                this.wristLeft.setPosition((180-(this.armPosition-20))/180);
            }

        }
    }

    public void raise() {
        this.setPositionAsync(armPosition + armDelta);
    }

    public void raise(double amt) {
        this.setPositionAsync(armPosition + amt);
    }

    public void raiseUnbounded(double amt) {
        this.setPositionAsyncUnbounded(armPosition + amt);
    }

    public void lower() {
        this.setPositionAsync(armPosition - armDelta);
    }

    public void lower(double amt) {
        this.setPositionAsync(armPosition - amt);
    }

    public void lowerUnbounded(double amt) {
        this.setPositionAsyncUnbounded(armPosition - amt);
    }

    public double bound(double degrees) {
        // this will keep the arm within its ok range of motion
        if (degrees >= 253) {
            return 253;
        } else if (degrees <= 0) {
            return 0;
        }
        return degrees;
    }

    public void reInit() {
        this.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void deactivate() {
        this.arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.arm.setPower(0);
    }

    public static class Gripper {
        public final Servo clawLeft;
        public final Servo clawRight;
        private boolean clawOpen;

        public Gripper(HardwareMap hardwareMap) {
            this.clawLeft = hardwareMap.get(Servo.class, "clawLeft");
            this.clawRight = hardwareMap.get(Servo.class, "clawRight");
        }


        public void close() {
            this.clawLeft.setPosition(1);
            this.clawRight.setPosition(0);
            this.clawOpen = true;
        }

        public void open() {
            this.clawLeft.setPosition(.85);
            this.clawRight.setPosition(.15);
            this.clawOpen = false;
        }

        public void release() {
            open();
        }

        public void toggle() {
            if (clawOpen) {
                this.close();
                this.clawOpen = false;
            } else {
                this.open();
                this.clawOpen = true;
            }
        }
    }

    public void setPower(double power) {
        this.arm.setPower(power);
    }
}
