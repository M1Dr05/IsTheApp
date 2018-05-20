package com.github.midros.istheapp.ui.fragments.keylog.adapter

import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.KeyData
import com.github.midros.istheapp.utils.ConstFun.firebaseOptions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.pawegio.kandroid.inflateLayout

/**
 * Created by luis rafael on 20/03/18.
 */
class KeysRecyclerAdapter(query: Query) : FirebaseRecyclerAdapter<KeyData, KeysViewHolder>(firebaseOptions(query)){

    private lateinit var interfaceKeysAdapter : InterfaceKeysAdapter

    override fun onDataChanged() {
        if (snapshots.size == 0) interfaceKeysAdapter.successResult(false)
        else interfaceKeysAdapter.successResult(true)
    }

    override fun onError(error: DatabaseError) {
        interfaceKeysAdapter.failedResult(error)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeysViewHolder =
            KeysViewHolder(parent.context.inflateLayout(R.layout.item_keylog, parent, false))

    override fun onBindViewHolder(holder: KeysViewHolder, position: Int, model: KeyData) =
            holder.bind(model)

    fun onRecyclerAdapterListener(interfaceKeysAdapter: InterfaceKeysAdapter){
        this.interfaceKeysAdapter = interfaceKeysAdapter
    }

}