package com.github.midros.istheapp.ui.fragments.photo.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.Photo
import com.github.midros.istheapp.ui.activities.gallery.ViewPhoto
import kotterknife.bindView

import com.github.midros.istheapp.utils.ConstFun.setImageUrl
import com.github.midros.istheapp.utils.Consts.NAME_IMAGE
import com.github.midros.istheapp.utils.Consts.URL_IMAGE
import com.pawegio.kandroid.IntentFor
import android.support.v4.app.ActivityOptionsCompat
import android.widget.*
import com.github.midros.istheapp.ui.activities.main.MainActivity

/**
 * Created by luis rafael on 20/03/18.
 */
class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val dateTime: TextView by bindView(R.id.date_item_photo)
    private val imgPhoto: ImageView by bindView(R.id.img_item_photo)
    val itemClick: CardView by bindView(R.id.item_click_photo)


    fun bind(item: Photo) = with(itemView) {
        dateTime.text = item.dateTime
        imgPhoto.setImageUrl(context, item.urlPhoto!!)
        itemClick.setOnClickListener {
            context.startViewImage(item.urlPhoto!!, item.nameRandom!!)
        }
    }

    private fun Context.startViewImage(urlImage: String, nameItem: String) {
        val intent = IntentFor<ViewPhoto>(this)
        intent.putExtra(URL_IMAGE, urlImage)
        intent.putExtra(NAME_IMAGE, nameItem)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this as MainActivity, imgPhoto, "profile")
        window.exitTransition = null
        startActivity(intent, options.toBundle())
    }

}