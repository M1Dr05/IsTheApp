package com.github.midros.istheapp.ui.fragments.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.midros.istheapp.R
import com.github.midros.istheapp.ui.activities.base.BaseFragment
import com.github.midros.istheapp.ui.widget.CustomCheckBox
import com.github.midros.istheapp.data.preference.DataSharePreference.getLockState
import com.github.midros.istheapp.data.preference.DataSharePreference.setLockState
import com.github.midros.istheapp.data.preference.DataSharePreference.getLockPin
import com.github.midros.istheapp.data.preference.DataSharePreference.setLockPin
import com.github.midros.istheapp.data.preference.DataSharePreference.getFinishAppState
import com.github.midros.istheapp.data.preference.DataSharePreference.setFinishAppState
import com.github.midros.istheapp.data.preference.DataSharePreference.setTimeFinishApp
import com.github.midros.istheapp.data.preference.DataSharePreference.getTimeFinishApp
import com.github.midros.istheapp.utils.ConstFun.find
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.inflateLayout
import com.pawegio.kandroid.longToast
import com.pawegio.kandroid.show
import kotterknife.bindView

/**
 * Created by luis rafael on 20/03/18.
 */
class SettingFragment : BaseFragment(){

    private fun getLockState() : Boolean = context!!.getLockState()
    private fun setLockState(state:Boolean) = context!!.setLockState(state)
    private fun getLockPin() : String = context!!.getLockPin()
    private fun setLockPin(pin:String) = context!!.setLockPin(pin)
    private fun getFinishAppState() : Boolean = context!!.getFinishAppState()
    private fun setFinishAppState(state:Boolean) = context!!.setFinishAppState(state)
    private fun getTimeFinishApp() : Int = context!!.getTimeFinishApp()
    private fun setTimeFinishApp(time:Int) = context!!.setTimeFinishApp(time)

    private val enableCodeAccess : LinearLayout by bindView(R.id.btn_enable_code_access)
    private val checkEnableCodeAccess : CustomCheckBox by bindView(R.id.check_enable_code_access)
    private val changeCodeAccess : LinearLayout by bindView(R.id.btn_change_code_access)
    private val enableAppFinish : LinearLayout by bindView(R.id.btn_enable_app_finih)
    private val checkEnableAppFinish : CustomCheckBox by bindView(R.id.check_enable_app_finish)
    private val changeTimeAppFinish : LinearLayout by bindView(R.id.btn_change_time_app_finish)
    private val txtTimeAppFinish: TextView by bindView(R.id.txt_time_finish_app)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_setting,container,false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init(){

        initValueTime()

        if (getLockState()) trueLockState() else falseLockState()
        if (getFinishAppState()) trueAppFinishState() else falseAppFinishState()

        enableCodeAccess.setOnClickListener {
            if (getLockState()) {
               falseLockState()
            } else{
                trueLockState()
            }
        }

        enableAppFinish.setOnClickListener {
            if (getFinishAppState()){
                falseAppFinishState()
            }else{
                trueAppFinishState()
            }
        }

        changeCodeAccess.setOnClickListener { showDialogCodeAccess() }
        changeTimeAppFinish.setOnClickListener { showDialogFinisApp() }
    }

    private fun falseLockState(){
        checkEnableCodeAccess.isChecked = false
        setLockState(false)
        changeCodeAccess.isEnabled = false
        changeCodeAccess.alpha = 0.3f
        //setLockPin("")
    }

    private fun trueLockState(){
        checkEnableCodeAccess.isChecked = true
        setLockState(true)
        changeCodeAccess.isEnabled = true
        changeCodeAccess.alpha = 1f
        if (getLockPin()=="") showDialogCodeAccess()
    }

    private fun falseAppFinishState(){
        checkEnableAppFinish.isChecked = false
        setFinishAppState(false)
        changeTimeAppFinish.isEnabled = false
        changeTimeAppFinish.alpha = 0.3f
    }

    private fun trueAppFinishState(){
        checkEnableAppFinish.isChecked = true
        setFinishAppState(true)
        changeTimeAppFinish.isEnabled = true
        changeTimeAppFinish.alpha = 1f
    }

    private fun initValueTime(){
        when(getTimeFinishApp()) {
            1000 -> txtTimeAppFinish.text = getString(R.string.one_minute)
            2000 -> txtTimeAppFinish.text = getString(R.string.two_minute)
            5000 -> txtTimeAppFinish.text = getString(R.string.five_minute)
        }
    }

    private fun showDialogCodeAccess(){
        val view = context!!.inflateLayout(R.layout.view_change_code_access)
        val edtCurrent = view.find<EditText>(R.id.current_access_code)
        val edtNew = view.find<EditText>(R.id.new_access_code)
        val edtRepeat = view.find<EditText>(R.id.repeat_access_code)

        if (getLockPin()=="") edtCurrent.hide() else edtCurrent.show()

        SweetAlertDialog(context!!,SweetAlertDialog.NORMAL_TYPE).apply {
            setCustomView(view)
            titleText = getString(R.string.change_code_access)
            confirmText = getString(R.string.change)
            cancelText = getString(android.R.string.cancel)
            showCancelButton(true)
            setCancelable(false)
            setConfirmClickListener {

                if (getLockPin()!="")
                if (edtCurrent.text.toString() != getLockPin()){
                    edtCurrent.error = getString(R.string.error_current_code_access)
                    edtCurrent.requestFocus()
                    edtCurrent.setText("")
                    return@setConfirmClickListener
                }
                if (edtNew.text.length > 4 || edtNew.text.length < 4){
                    edtNew.error = getString(R.string.error_code_access)
                    edtNew.requestFocus()
                    edtNew.setText("")
                } else if (edtRepeat.text.length > 4 || edtRepeat.text.length < 4){
                    edtRepeat.error = getString(R.string.error_code_access)
                    edtRepeat.requestFocus()
                    edtRepeat.setText("")
                }else if (edtNew.text.toString() != edtRepeat.text.toString()){
                    longToast(R.string.error_not_match_code_access)
                    edtNew.requestFocus()
                    edtNew.setText("")
                    edtRepeat.setText("")
                }else {
                    setLockPin(edtNew.text.toString())
                    dismissWithAnimation()
                }

            }
            setCancelClickListener {
                dismissWithAnimation()
                if (getLockPin()=="") falseLockState()
            }
            show()
        }
    }

    private fun showDialogFinisApp(){
        val view = context!!.inflateLayout(R.layout.view_change_time_finish_app)
        val clickOneMinute = view.find<LinearLayout>(R.id.click_one_minute_finish_app)
        val clickTwoMinute = view.find<LinearLayout>(R.id.click_two_minute_finish_app)
        val clickFiveMinute = view.find<LinearLayout>(R.id.click_five_minute_finish_app)
        val checkOneMinute = view.find<CustomCheckBox>(R.id.check_one_minute_finish_app)
        val checkTwoMinute = view.find<CustomCheckBox>(R.id.check_two_minute_finish_app)
        val checkFiveMinute = view.find<CustomCheckBox>(R.id.check_five_minute_finish_app)

        val dialog = SweetAlertDialog(context,SweetAlertDialog.NORMAL_TYPE).apply {
            hideConfirmButton()
            setCustomView(view)
            titleText = getString(R.string.change_app_finish_time)
            show()
        }

        when(getTimeFinishApp()) {
            1000 -> checkOneMinute.isChecked = true
            2000 -> checkTwoMinute.isChecked = true
            5000 -> checkFiveMinute.isChecked = true
        }

        clickOneMinute.setOnClickListener { setTimeFinishApp(1000)
            initValueTime()
            dialog.dismissWithAnimation()
        }
        clickTwoMinute.setOnClickListener { setTimeFinishApp(2000)
            initValueTime()
            dialog.dismissWithAnimation()
        }
        clickFiveMinute.setOnClickListener { setTimeFinishApp(5000)
            initValueTime()
            dialog.dismissWithAnimation()
        }
    }

}