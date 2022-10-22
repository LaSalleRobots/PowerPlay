package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Two-Controller Driver-Controlled")
public class FullPowerDriver extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();
        Robot robot = new Robot(hardwareMap, time);
        double x;
        double y;
        double r;
        double target;
        boolean MakerIndustriesIsTheBest = true;
        boolean debounceX = false;
        boolean debounceF = false;
        boolean fieldCentric = false;
        LiftControlMode liftControlMode = LiftControlMode.ManualControl;
        //Quickly tweak sensitivity coefficients here
        double gpad1MoveSpeed = 0.75;
        double gpad1RotationSpeed = 0.25;
        double gpad2MoveSpeed = 0.25;
        double gpad2RotationSpeed = 0.05;
        double ManualModeLiftSensitivity = 50;

        waitForStart();

        while (MakerIndustriesIsTheBest) {
            //Movement section
            {
                //Note that both gamepads get control over robot movement, and can complement or counteract each other.
                // prematurely combines joystick values, this is purely organizational
                x = ((gpad1MoveSpeed * gamepad1.left_stick_x) + (gpad2MoveSpeed * gamepad2.left_stick_x));
                y = ((gpad1MoveSpeed * gamepad1.left_stick_y) + (gpad2MoveSpeed * gamepad2.left_stick_y));
                r = ((gpad1RotationSpeed * gamepad1.right_stick_x) + (gpad2RotationSpeed * gamepad2.right_stick_x));
                //I'm not partial to field centric, but I still want it to be an available feature.
                if (fieldCentric) {
                    //robot.getHeading() may be partially or not at all functional. Good luck, traveler.
                    robot.drive.calculateDirectionsFieldCentric(x, y, -r, robot.getHeading());
                }
                else {
                    //applies drive values. Notice the negative R.
                    robot.drive.calculateDirections(x, y, -r);
                }
                //Applies... power or something. I think this works both for field-centric and not.
                robot.drive.applyPower();
                //Toggle field centric (stole the whole debouncer from the claw code)
                {
                    if (gamepad1.triangle) {
                        if (debounceF = false) {
                            debounceF = true;
                            if (fieldCentric) {
                                fieldCentric = false;

                            }
                            else {
                                fieldCentric = true;
                            }
                        }

                    }
                    {
                        if (debounceF) {
                            if (!gamepad1.triangle) {
                                debounceF = false;
                            }
                        }
                    }
                }
            }

            //Lift section
            {
                if (liftControlMode == LiftControlMode.ManualControl) {
                    // operation while in Manual Control state
                    //manual sequence: relative lift target calculations.
                    target = (robot.lift.getPosition() + (ManualModeLiftSensitivity * gamepad2.right_stick_y));
                    //requires an integer. Not sure if this part works.
                    robot.lift.setPositionAsync((int) target);


                    //state machine exit condition
                    if (gamepad2.dpad_up || gamepad2.dpad_down ||
                        gamepad2.dpad_right || gamepad2.dpad_left) {
                        liftControlMode = LiftControlMode.PresetControl;
                    }
                }

                if (liftControlMode == LiftControlMode.PresetControl) {
                    // operation while in Preset Control state
                    if (gamepad2.dpad_up) {
                        robot.lift.setPositionAsync(robot.lift.LARGE);
                    }
                    if (gamepad2.dpad_down) {
                        robot.lift.setPositionAsync(robot.lift.SMALL);
                    }
                    if (gamepad2.dpad_left) {
                        robot.lift.setPositionAsync(robot.lift.MIDDLE);
                    }
                    if (gamepad2.dpad_right) {
                        robot.lift.setPositionAsync(0);
                    }

                    // state exit condition
                    if (!robot.lift.isActive()) {
                        liftControlMode = LiftControlMode.ManualControl;
                    }
                }
            }

            //Claw? (needs actual claw stuff)
            {
                if (gamepad2.x) {
                    //I don't know how to use the "Debouncer" file in the directory, and I remember from last year that it was a little jank. Making my own.
                    if (debounceX = false) {
                        debounceX = true;
                        //claw stuff here
                        robot.grabber.toggle();
                        }
                    }

                }
                //debouncer stuff
                {
                    //Reset debouncer boolean when found to be on when button has been released.
                    if (debounceX) {
                        if (!gamepad2.x) {
                            debounceX = false;
                        }
                    }
                }
            if (!opModeIsActive()) {break;}
            }

        }
    private enum LiftControlMode { ManualControl, PresetControl }
    }

