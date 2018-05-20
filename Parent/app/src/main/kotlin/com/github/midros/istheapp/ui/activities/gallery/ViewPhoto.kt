package com.github.midros.istheapp.ui.activities.gallery

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.KeyEvent
import android.view.WindowManager
import com.github.midros.istheapp.R
import com.github.midros.istheapp.ui.widget.TouchImageView
import kotterknife.bindView
import com.github.midros.istheapp.utils.ConstFun.setImageUrl
import com.github.midros.istheapp.utils.Consts.URL_IMAGE

/**
 * Created by luis rafael on 28/03/18.
 */
class ViewPhoto : FragmentActivity() {

    private val imgPhoto: TouchImageView by bindView(R.id.img_view_photo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_photo)
        setImage()
    }

    override fun onResume() {
        super.onResume()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun setImage() {
        val url = intent.getStringExtra(URL_IMAGE)
        imgPhoto.setImageUrl(this, url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            supportFinishAfterTransition()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

}