package com.github.midros.istheapp.ui.fragments.photo.adapter

import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.Photo
import com.github.midros.istheapp.utils.ConstFun.firebaseOptions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.pawegio.kandroid.inflateLayout
import com.github.midros.istheapp.utils.Consts.TAG
import com.jakewharton.rxbinding2.view.RxView
import com.pawegio.kandroid.e

/**
 * Created by luis rafael on 20/03/18.
 */
class PhotoRecyclerAdapter(query: Query) : FirebaseRecyclerAdapter<Photo, PhotoViewHolder>(firebaseOptions(query)) {

    private lateinit var interfacePhotoAdapter: InterfacePhotoAdapter

    override fun onDataChanged() {
        if (snapshots.size == 0) interfacePhotoAdapter.successResult(false)
        else interfacePhotoAdapter.successResult(true)
    }

    override fun onError(error: DatabaseError) {
        interfacePhotoAdapter.failedResult(error)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
            PhotoViewHolder(parent.context.inflateLayout(R.layout.item_photo, parent, false))

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int, model: Photo) {
        holder.bind(getItem(position))
        val key = getRef(position).key
        RxView.longClicks(holder.itemClick).subscribe({
            interfacePhotoAdapter.onLongClickDeleteFilePhoto(key, model.nameRandom!!)
        }, { e(TAG, it.message.toString()) })

    }

    fun onRecyclerAdapterListener(interfacePhotoAdapter: InterfacePhotoAdapter) {
        this.interfacePhotoAdapter = interfacePhotoAdapter
    }

}