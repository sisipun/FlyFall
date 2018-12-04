package io.cucumber.utils

object TimeUtils {

    private const val MILLIS_IN_SECOND = 1000


    fun secondsToMillis(seconds: Float): Float = seconds * MILLIS_IN_SECOND

}