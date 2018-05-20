package com.github.midros.child.utils.checkForegroundApp

import android.content.Context
import com.pawegio.kandroid.runOnUiThread

/**
 * Created by luis rafael on 20/03/18.
 */
class CheckApp(private val context: Context, private val action: (app: String?) -> Unit) {

    private val detector: CheckDetector = CheckForegroundApp()

    private var timeout: Long = 1000

    private val thread: Thread = Thread(Runnable {
        while (true) {
            try {
                Thread.sleep(timeout)
                runOnUiThread {
                    val foregroundApp = detector.getForegroundApp(context)
                    action(foregroundApp)
                }
            } catch (e: InterruptedException) {
                break
            }
        }
    })

    fun timeout(timeout: Long): CheckApp {
        this.timeout = timeout
        return this
    }

    fun start(): CheckApp {
        thread.start()
        return this
    }

    fun stop() {
        thread.interrupt()
    }


}