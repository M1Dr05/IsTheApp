package com.github.midros.istheapp.ui.fragments.message

import android.annotation.SuppressLint
import android.os.Bundle
import com.github.clans.fab.FloatingActionButton
import android.support.v7.widget.GridLayoutManager
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import com.github.midros.istheapp.R
import com.github.midros.istheapp.ui.activities.base.BaseFragment
import com.github.midros.istheapp.ui.activities.main.MainActivity
import com.github.midros.istheapp.ui.animation.OvershootInRightAnimator
import com.github.midros.istheapp.ui.fragments.message.adapter.SmsRecyclerAdapter
import com.github.midros.istheapp.ui.widget.ItemOffsetDecoration
import com.github.midros.istheapp.ui.widget.CustomRecyclerView
import com.github.midros.istheapp.ui.widget.OnScrollListener
import com.github.midros.istheapp.utils.Consts.FIELD_ONE
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.runDelayedOnUiThread
import com.pawegio.kandroid.show
import kotterknife.bindView
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by luis rafael on 17/03/18.
 */
class MessageFragment : BaseFragment(), InterfaceViewMessage {

    private val viewProgress: LinearLayout by bindView(R.id.progress_placeholder)
    private val viewNotHave: LinearLayout by bindView(R.id.not_have_placeholder)
    private val viewFailed: LinearLayout by bindView(R.id.failed_placeholder)
    private val txtNotHave: TextView by bindView(R.id.txt_not_have_get)
    private val txtFailed: TextView by bindView(R.id.txt_failed_get)
    private val list: CustomRecyclerView by bindView(R.id.list)
    private val floatingButton: FloatingActionButton by bindView(R.id.floating_button)

    @Inject
    lateinit var interactor: InterfaceInteractorMessage<InterfaceViewMessage>
    @Inject
    @field:Named(FIELD_ONE)
    lateinit var layoutM: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_message, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (getComponent() != null) {
            getComponent()!!.inject(this)
            interactor.onAttach(this)
            interactor.setRecyclerAdapter()
            timeConnection()
        }
    }

    private fun timeConnection() {
        runDelayedOnUiThread(13000) {
            if (viewProgress.isShown) failedResult(Throwable(getString(R.string.error_database_connection)))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.nav_clear_sms).isVisible = true
    }

    override fun onStart() {
        super.onStart()
        interactor.startRecyclerAdapter()
    }

    override fun setRecyclerAdapter(smsRecyclerAdapter: SmsRecyclerAdapter) {
        list.apply {
            addItemDecoration(ItemOffsetDecoration())
            itemAnimator = OvershootInRightAnimator()
            itemAnimator.addDuration = 500
            itemAnimator.removeDuration = 500
            layoutManager = layoutM
            adapter = smsRecyclerAdapter
            recycledViewPool.clear()
            addOnScrollListener(OnScrollListener(floatingButton, layoutM))
        }
        floatingButton.setOnClickListener {
            list.smoothScrollToPosition(smsRecyclerAdapter.itemCount - 1)
        }
    }

    override fun successResult(boolean: Boolean) {
        if (boolean) {
            viewProgress.hide()
            viewFailed.hide()
            viewNotHave.hide()
            list.show()
            (context as MainActivity).setIndicatorVisible(2)
        } else {
            viewProgress.hide()
            viewFailed.hide()
            list.hide()
            viewNotHave.show()
            txtNotHave.text = getString(R.string.not_have_sms_yet)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun failedResult(throwable: Throwable) {
        viewProgress.hide()
        viewNotHave.hide()
        list.hide()
        viewFailed.show()
        txtFailed.text = "${getString(R.string.failed_sms)}, ${throwable.message}"
    }

    override fun onStop() {
        super.onStop()
        interactor.stopRecyclerAdapter()
    }

    override fun onDetach() {
        clearDisposable()
        interactor.onDetach()
        super.onDetach()
    }

}