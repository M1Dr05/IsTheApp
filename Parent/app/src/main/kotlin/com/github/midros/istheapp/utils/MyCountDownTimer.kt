package com.github.midros.istheapp.utils

import android.os.CountDownTimer

/**
 * Created by luis rafael on 19/03/18.
 */
class MyCountDownTimer(startTime: Long, interval: Long, private val func: () -> Unit) : CountDownTimer(startTime, interval) {
    override fun onFinish() = func()
    override fun onTick(millisUntilFinished: Long) {}
}