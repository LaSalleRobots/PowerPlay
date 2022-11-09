package org.firstinspires.ftc.teamcode;

class Debouncer {
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
}
