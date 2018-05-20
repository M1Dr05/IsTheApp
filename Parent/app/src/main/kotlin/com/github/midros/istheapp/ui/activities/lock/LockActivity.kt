package com.github.midros.istheapp.ui.activities.lock

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.KeyEvent
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.andrognito.pinlockview.IndicatorDots
import com.andrognito.pinlockview.PinLockListener
import com.andrognito.pinlockview.PinLockView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.github.midros.istheapp.R
import com.github.midros.istheapp.ui.activities.base.BaseActivity
import com.github.midros.istheapp.data.preference.DataSharePreference.getLockPin
import kotterknife.bindView

/**
 * Created by luis rafael on 28/03/18.
 */
class LockActivity : BaseActivity(), PinLockListener {

    private val indicators: IndicatorDots by bindView(R.id.indicator_dots)
    private val lockView: PinLockView by bindView(R.id.pin_lock_view)
    private val txtMsg: TextView by bindView(R.id.txt_msg_lock)

    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_lock)
        startLock()
        initializeVibrator()
    }

    private fun initializeVibrator() {
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    private fun startLock() {
        lockView.attachIndicatorDots(indicators)
        lockView.setPinLockListener(this)
        lockView.pinLength = 4
        indicators.indicatorType = IndicatorDots.IndicatorType.FILL_WITH_ANIMATION
    }

    override fun onEmpty() {}

    override fun onComplete(pin: String?) {
        if (getLockPin() == pin) {
            finish()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) vibrator.vibrate(VibrationEffect.createOneShot(150, 10))
            else vibrator.vibrate(150)
            lockView.resetPinLockView()
            YoYo.with(Techniques.Shake).duration(200).pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT).playOn(txtMsg)
        }
    }

    override fun onPinChange(pinLength: Int, intermediatePin: String?) {}

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) return false
        return super.onKeyDown(keyCode, event)
    }

}