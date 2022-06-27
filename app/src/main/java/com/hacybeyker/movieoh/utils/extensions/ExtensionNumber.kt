package com.hacybeyker.movieoh.utils.extensions

fun Double.format(decimal: Int): String {
    return "%.${decimal}f".format(this)
}

const val HOUR_IN_MINUTE = 60
const val HOUR = "h"
const val MINUTE = "min"
const val SPACE = " "

fun Int.getRuntime(): String {
    val hora = this / HOUR_IN_MINUTE
    val minute = this % HOUR_IN_MINUTE
    val timeCompose: StringBuilder = StringBuilder()

    if (hora > 0) {
        timeCompose.append("$hora $HOUR")
        timeCompose.append(SPACE)
    }
    if (minute > 0) {
        timeCompose.append("$minute $MINUTE")
    }

    return timeCompose.toString()
}
