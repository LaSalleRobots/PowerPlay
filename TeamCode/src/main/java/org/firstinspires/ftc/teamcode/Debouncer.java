package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Debouncer {
    private boolean internalLock = false;

    public Debouncer() {}

    public boolean isPressed(boolean btn) {
        if (btn) {
            //I don't know how to use the "Debouncer" file in the directory, and I remember from last year that it was a little jank. Making my own.
            if (!internalLock) {
                internalLock = true;
                return true;
            }
        }
        //Reset debouncer boolean when found to be on when button has been released.
        if (internalLock) {
            if (!btn) {
                internalLock = false;
            }
        }
        return false;
    }
}