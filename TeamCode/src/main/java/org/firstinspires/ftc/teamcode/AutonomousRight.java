package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autonomous Right")
public class AutonomousRight extends AutonomousWithSensorRight {
    public static void main(String[] args) {
        final int directionCoefficient = -1;
        AutonomousWithSensorRight auto = new AutonomousWithSensorRight();



        //auto.runOpMode(-1);
    }
}
;