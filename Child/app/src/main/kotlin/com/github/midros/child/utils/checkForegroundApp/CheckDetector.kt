package com.github.midros.child.utils.checkForegroundApp

import android.content.Context

/**
 * Created by luis rafael on 20/03/18.
 */
interface CheckDetector {

    fun getForegroundApp(context: Context): String?

}