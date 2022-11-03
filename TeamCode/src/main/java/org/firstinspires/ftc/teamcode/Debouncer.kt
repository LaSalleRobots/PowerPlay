package org.firstinspires.ftc.teamcode



class Debouncer {
    private var lock = false

    fun isPressed(input: Boolean) : Boolean {
        if (input) {
            if (!lock) {
                lock = true
                return true
            }
        } else {
            if (lock) {
                lock = false
            }
        }
        return false
    }

    fun ifPressed(input: Boolean, func: () -> Unit) {
        if (isPressed(input)) {
            func()
        }
    }
}