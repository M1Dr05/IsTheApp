package com.github.midros.child.utils.checkForegroundApp

import android.app.AppOpsManager
import android.content.Context

/**
 * Created by luis rafael on 20/03/18.
 */
object CheckPermission{
    fun Context.hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow("android:get_usage_stats",
                android.os.Process.myUid(), packageName)
        return mode == AppOpsManager.MODE_ALLOWED
    }
}