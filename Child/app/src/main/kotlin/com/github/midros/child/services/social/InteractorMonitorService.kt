package com.github.midros.child.services.social

import android.content.Context
import android.content.Intent
import com.github.midros.child.data.rxFirebase.InterfaceFirebase
import com.github.midros.child.utils.Consts.CHILD_PERMISSION
import com.github.midros.child.utils.Consts.CHILD_SOCIAL_MS
import com.github.midros.child.utils.Consts.RESTART_MONITOR_RECEIVER
import com.github.midros.child.utils.Consts.SOCIAL
import com.github.midros.child.utils.Consts.TAG
import com.pawegio.kandroid.e
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by luis rafael on 27/03/18.
 */
class InteractorMonitorService @Inject constructor(private val context: Context, private val firebase: InterfaceFirebase) : InterfaceMonitorService {

    override fun gerSocialStatus() {
        firebase.valueEvent("$SOCIAL/$CHILD_SOCIAL_MS")
                .map { data -> data.exists() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it) context.sendBroadcast(Intent(RESTART_MONITOR_RECEIVER))
                }, { e(TAG, it.message.toString()) })
    }

    override fun setPermission(status: Boolean) {
        firebase.getDatabaseReference("$SOCIAL/$CHILD_PERMISSION").setValue(status)
    }

}