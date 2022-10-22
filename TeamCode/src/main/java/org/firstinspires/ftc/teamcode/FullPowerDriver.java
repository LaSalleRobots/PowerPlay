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

        waitForStart();

        while (MakerIndustriesIsTheBest) {
            //Movement section
            {
                //Note that both gamepads get control over robot movement, and can complement or counteract each other.
                // prematurely combines joystick values, this is purely organizational
                x = ((0.75 * gamepad1.left_stick_x) + (0.25 * gamepad2.left_stick_x));
                y = ((0.75 * gamepad1.left_stick_y) + (0.25 * gamepad2.left_stick_y));
                r = ((0.25 * gamepad1.right_stick_x) + (0.05 * gamepad2.right_stick_x));
                //applies drive values. Notice the negative R.
                robot.drive.calculateDirections(x, y, -r);
                robot.drive.applyPower();
            }

            //Lift section
            {
                //manual sequence: relative lift target calculations. Change this coefficient if too sensitive.
                target = (robot.lift.getPosition() + (50 * gamepad2.right_stick_y));
                //requires an integer. Not sure if this part works.
                robot.lift.setPositionAsync((int) target);

                //dPad section. Better way to do this? I don't care.
                {
                    if (gamepad2.dpad_up) {
                        robot.lift.setPosition(robot.lift.LARGE);
                    }
                    if (gamepad2.dpad_down) {
                        robot.lift.setPosition(robot.lift.SMALL);
                    }
                    if (gamepad2.dpad_left) {
                        robot.lift.setPosition(robot.lift.MIDDLE);
                    }
                    if (gamepad2.dpad_right) {
                        robot.lift.setPosition(0);
                    }
                }
            }

            //Claw? (needs actual claw stuff)
            {
                if (gamepad2.x) {
                    //I don't know how to use the "Debouncer" file in the directory, and I remember from last year that it was a little jank. Making my own.
                    if (debounceX = false) {
                        debounceX = true;
                        //Do claw stuff here
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
            }
        }
    }
}
