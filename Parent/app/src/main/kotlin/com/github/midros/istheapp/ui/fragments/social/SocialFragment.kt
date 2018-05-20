package com.github.midros.istheapp.ui.fragments.social

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.Social
import com.github.midros.istheapp.ui.activities.base.BaseFragment
import com.github.midros.istheapp.utils.Consts
import com.google.firebase.database.DataSnapshot
import com.pawegio.kandroid.e
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import kotterknife.bindView
import javax.inject.Inject

/**
 * Created by luis rafael on 17/03/18.
 */
class SocialFragment : BaseFragment(), InterfaceViewSocial {


    private val viewDone: ImageView by bindView(R.id.done_social)
    private val imgPermission: ImageView by bindView(R.id.img_permission_state)
    private val viewProgress: LinearLayout by bindView(R.id.progress_placeholder)
    private val viewCredentials: LinearLayout by bindView(R.id.view_credentials)
    private val viewFailed: LinearLayout by bindView(R.id.failed_placeholder)
    private val txtFailed: TextView by bindView(R.id.txt_failed_get)
    private val txtEmail: TextView by bindView(R.id.txt_email_get_social)
    private val txtPass: TextView by bindView(R.id.txt_pass_get_social)

    @Inject
    lateinit var interactor: InterfaceInteractorSocial<InterfaceViewSocial>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_social, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (getComponent() != null) {
            getComponent()!!.inject(this)
            interactor.onAttach(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.nav_clear_social).isVisible = true
    }

    override fun onStart() {
        super.onStart()
        interactor.valueEventSocial()
        interactor.valueEventEnablePermission()
    }

    override fun successResult(dataSnapshot: DataSnapshot) {
        if (dataSnapshot.exists()) {
            val social = dataSnapshot.getValue(Social::class.java)!!
            viewCredentials.show()
            viewProgress.hide()
            viewDone.show()
            txtEmail.text = social.emailSocial
            txtPass.text = social.passSocial
        } else {
            viewCredentials.show()
            viewDone.hide()
            viewProgress.show()
            txtEmail.text = getString(R.string.ellipsis)
            txtPass.text = getString(R.string.ellipsis)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun failedResult(throwable: Throwable) {
        viewCredentials.hide()
        viewFailed.show()
        txtFailed.text = "${getString(R.string.failed_social)}, ${throwable.message.toString()}"
    }

    override fun setValuePermission(dataSnapshot: DataSnapshot) {
        try {
            if (dataSnapshot.exists()) {
                if (dataSnapshot.value as Boolean) imgPermission.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_enable_24dp)
                else {
                    imgPermission.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_disable_24dp)
                    failedResult(Throwable(getString(R.string.message_permission_social_disable)))
                }
            } else {
                imgPermission.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_disable_24dp)
            }
        } catch (t: Throwable) {
            e(Consts.TAG, t.message.toString())
        }
    }

    override fun onDetach() {
        clearDisposable()
        interactor.onDetach()
        super.onDetach()
    }

}