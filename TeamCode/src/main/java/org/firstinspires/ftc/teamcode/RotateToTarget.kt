package org.firstinspires.ftc.teamcode
import com.qualcomm.robotcore.util.ElapsedTime
//what the hell is this import below and why did it make my error go away
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap

val time = ElapsedTime()
val robot = Robot(hardwareMap, time)

class RotateToTarget {
    fun rotateTo(input: Double) {
        if (input < robot.heading) {
            while (input < robot.heading) {
                //a value of 2 clips the function to be 100% on each drive motor instead of 50% (to a sum of 100%)
                robot.drive.calculateDirections(0.0, 0.0, -2.0)
                robot.drive.applyPower()
            }
            robot.drive.off()
        }
        if (input > robot.heading) {
            while (input > robot.heading) {
                robot.drive.calculateDirections(0.0,0.0,2.0)
                robot.drive.applyPower()
            }
            robot.drive.off()
        }
    }
}