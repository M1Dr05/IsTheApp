package com.github.midros.istheapp.ui.fragments.photo

import android.annotation.SuppressLint
import android.os.Bundle
import com.github.clans.fab.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.clans.fab.FloatingActionMenu
import com.github.midros.istheapp.R
import com.github.midros.istheapp.ui.activities.base.BaseFragment
import com.github.midros.istheapp.ui.fragments.photo.adapter.PhotoRecyclerAdapter
import com.github.midros.istheapp.ui.widget.ItemOffsetDecoration
import com.github.midros.istheapp.ui.widget.OnScrollListener
import com.github.midros.istheapp.utils.ConstFun.customAnimationMenu
import com.github.midros.istheapp.utils.Consts.FIELD_TWO
import com.github.midros.istheapp.utils.Consts.FRONT_FACING_CAMERA
import com.github.midros.istheapp.utils.Consts.REAR_FACING_CAMERA
import com.github.midros.istheapp.utils.Consts.TAG
import com.github.midros.istheapp.data.preference.DataSharePreference.setStateAlertShow
import com.github.midros.istheapp.data.preference.DataSharePreference.getStateAlertShow
import com.github.midros.istheapp.ui.activities.main.MainActivity
import com.github.midros.istheapp.ui.animation.SlideInUpAnimator
import com.github.midros.istheapp.ui.widget.CustomRecyclerView
import com.google.firebase.database.DataSnapshot
import com.pawegio.kandroid.e
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.runDelayedOnUiThread
import com.pawegio.kandroid.show
import kotterknife.bindView
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by luis rafael on 17/03/18.
 */
class PhotoFragment : BaseFragment(), InterfaceViewPhoto {

    private val viewNotHave: LinearLayout by bindView(R.id.not_have_placeholder)
    private val txtNotHave: TextView by bindView(R.id.txt_not_have_get)
    private val viewProgress: LinearLayout by bindView(R.id.progress_placeholder)
    private val viewFailed: LinearLayout by bindView(R.id.failed_placeholder)
    private val txtFailed: TextView by bindView(R.id.txt_failed_get)
    private val list: CustomRecyclerView by bindView(R.id.list)
    private val imgState: ImageView by bindView(R.id.img_photo_state)
    private val imgPermission: ImageView by bindView(R.id.img_permission_state)
    private val progressGetPhoto: ProgressBar by bindView(R.id.progress_get_photo)
    private val floatingButton: FloatingActionButton by bindView(R.id.floating_button_photo)

    private val menuGetPhoto: FloatingActionMenu by bindView(R.id.menu_get_photo)
    private val menuItemGetFrontPhoto: FloatingActionButton by bindView(R.id.menu_item_get_front_photo)
    private val menuItemGetRearPhoto: FloatingActionButton by bindView(R.id.menu_item_get_rear_photo)

    @Inject
    @field:Named(FIELD_TWO)
    lateinit var layoutM: GridLayoutManager
    @Inject
    lateinit var interactor: InterfaceInteractorPhoto<InterfaceViewPhoto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_photo, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (getComponent() != null) {
            getComponent()!!.inject(this)
            interactor.onAttach(this)
            interactor.setRecyclerAdapter()
            interactor.startRecyclerAdapter()
            interactor.valueEventEnablePhoto()
            interactor.valueEventEnablePermission()
            onClick()
            menuAction()
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
        menu.findItem(R.id.nav_clear_photo).isVisible = true
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun menuAction() {
        customAnimationMenu(menuGetPhoto, R.drawable.ic_add_a_photo_black_24dp, R.drawable.ic_close_white_24dp)
        menuGetPhoto.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
            if (menuGetPhoto.isOpened) {
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    closeMenu()
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    private fun closeMenu() {
        menuGetPhoto.close(true)
        menuGetPhoto.isClickable = false
    }

    private fun onClick() {
        menuItemGetFrontPhoto.setOnClickListener {
            closeMenu()
            progressGetPhoto.show()
            interactor.getPhotoCamera(FRONT_FACING_CAMERA)
        }
        menuItemGetRearPhoto.setOnClickListener {
            closeMenu()
            progressGetPhoto.show()
            interactor.getPhotoCamera(REAR_FACING_CAMERA)
        }
    }

    override fun successResult(boolean: Boolean) {
        if (boolean) {
            viewProgress.hide()
            progressGetPhoto.hide()
            viewNotHave.hide()
            viewFailed.hide()
            list.show()
            (context as MainActivity).setIndicatorVisible(3)
        } else {
            viewProgress.hide()
            progressGetPhoto.hide()
            viewFailed.hide()
            list.hide()
            viewNotHave.show()
            txtNotHave.text = getString(R.string.not_have_photo_yet)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun failedResult(throwable: Throwable) {
        viewProgress.hide()
        viewNotHave.hide()
        list.hide()
        viewFailed.show()
        txtFailed.text = "${getString(R.string.failed_photo)}, ${throwable.message}"
    }

    override fun setRecyclerAdapter(recyclerAdapter: PhotoRecyclerAdapter) {
        list.apply {
            addItemDecoration(ItemOffsetDecoration())
            itemAnimator = SlideInUpAnimator()
            itemAnimator.addDuration = 600
            itemAnimator.removeDuration = 600
            layoutManager = layoutM
            adapter = recyclerAdapter
            recycledViewPool.clear()
            addOnScrollListener(OnScrollListener(floatingButton, layoutM))
        }
        floatingButton.setOnClickListener {
            list.smoothScrollToPosition(recyclerAdapter.itemCount - 1)
        }

    }

    override fun setValueState(dataSnapshot: DataSnapshot) {
        try {
            if (dataSnapshot.exists()) {
                if (dataSnapshot.value as Boolean) imgState.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_enable_24dp)
                else
                    imgState.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_disable_24dp)
            } else {
                imgState.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_disable_24dp)
            }
        } catch (t: Throwable) {
            e(TAG, t.message.toString())
        }
    }

    override fun setValuePermission(dataSnapshot: DataSnapshot) {
        try {
            if (dataSnapshot.exists()) {
                if (dataSnapshot.value as Boolean) {
                    imgPermission.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_enable_24dp)
                    activity!!.setStateAlertShow(true)
                } else {
                    imgPermission.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_disable_24dp)
                    progressGetPhoto.hide()
                    if (activity!!.getStateAlertShow())
                        showDialog(SweetAlertDialog.ERROR_TYPE, R.string.ops, getString(R.string.message_dialog_permission_photo_disable),
                                android.R.string.ok) {
                            setConfirmClickListener {
                                context.setStateAlertShow(false)
                                hideDialog()
                            }
                            show()
                        }
                }
            } else {
                imgPermission.background = ContextCompat.getDrawable(activity!!, R.drawable.ic_status_key_disable_24dp)
            }
        } catch (t: Throwable) {
            e(TAG, t.message.toString())
        }
    }

    override fun onDetach() {
        clearDisposable()
        interactor.stopRecyclerAdapter()
        interactor.onDetach()
        super.onDetach()
    }
}