package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Two-Controller Driver-Controlled")
public class TwoControllerDriverControlled extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime time = new ElapsedTime();
        Robot robot = new Robot(hardwareMap, time);
        double x;
        double y;
        double r;
        double target;
        boolean MakerIndustriesIsTheBest = true;
        Debouncer dx = new Debouncer();
        Debouncer df = new Debouncer();
        boolean fieldCentric = false;
        LiftControlMode liftControlMode = LiftControlMode.ManualControl;
        //Quickly tweak sensitivity coefficients here
        double gpad1MoveSpeed = 0.8;
        double gpad1RotationSpeed = 0.4;
        double gpad2MoveSpeed = 0.4;
        double gpad2RotationSpeed = 0.2;
        //negative coefficient to account for backwards lift control. Invert as needed.
        double ManualModeLiftSensitivity = -50;

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
                } else {
                    //applies drive values. Notice the negative R.
                    robot.drive.calculateDirections(x, y, -r);
                }
                //Applies... power or something. I think this works both for field-centric and not.
                robot.drive.applyPower();
                //Toggle field centric (stole the whole debouncer from the claw code)
                {
                    if (df.isPressed(gamepad1.triangle || gamepad2.x)) {
                        fieldCentric = !fieldCentric;
                    }
                }
            }

            //Lift section
            {
                if (liftControlMode == LiftControlMode.HoldPosition) {
                    // hold the position of the state machine
                    robot.lift.setPositionAsync(robot.lift.getTarget());

                    // exit conditions
                    if (stickMoved(gamepad2)) {
                        liftControlMode = LiftControlMode.ManualControl;
                    }
                    if (dPad(gamepad2) || gamepad2.left_bumper) {
                        liftControlMode = LiftControlMode.PresetControl;
                    }
                }
                if (liftControlMode == LiftControlMode.ManualControl) {
                    // operation while in Manual Control state
                    target = (robot.lift.getPosition() + (ManualModeLiftSensitivity * gamepad2.right_stick_y));
                    robot.lift.setPositionAsync((int) target);


                    //state machine exit condition
                    if (dPad(gamepad2) || gamepad2.left_bumper) {
                        liftControlMode = LiftControlMode.PresetControl;
                    }
                    if (gamepad2.right_stick_y == 0) {
                        liftControlMode = LiftControlMode.HoldPosition;
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
                    if (gamepad2.left_bumper) {
                        robot.lift.setPositionAsync(100);
                    }

                    // state exit condition

                    if (stickMoved(gamepad2) || gamepad2.b) {
                        liftControlMode = LiftControlMode.ManualControl;
                    }
                    if (!robot.lift.isActive()) {
                        liftControlMode = LiftControlMode.HoldPosition;
                    }
                }
            }

            //Claw? (needs actual claw stuff)
            {
                if (dx.isPressed(gamepad2.cross || gamepad2.a)) {
                    //I don't know how to use the "Debouncer" file in the directory, and I remember from last year that it was a little jank. Making my own.
                    robot.grabber.toggle();
                }
            }
            if (!opModeIsActive()) {
                break;
            }
        }

        }
    private enum LiftControlMode { ManualControl, PresetControl, HoldPosition, Home }

    private boolean dPad(Gamepad g) {
        return g.dpad_up || g.dpad_down ||
                g.dpad_right || g.dpad_left;
    }
    private boolean stickMoved(Gamepad g) {
        return g.right_stick_y > 0.1 || g.right_stick_y < -0.1;
    }
    }

