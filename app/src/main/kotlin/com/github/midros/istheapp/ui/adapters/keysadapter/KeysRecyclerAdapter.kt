package com.github.midros.istheapp.ui.adapters.keysadapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.KeyData
import com.github.midros.istheapp.ui.adapters.basedapter.BaseAdapter
import com.github.midros.istheapp.utils.ConstFun
import com.github.midros.istheapp.utils.ConstFun.firebaseOptions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.pawegio.kandroid.inflateLayout

/**
 * Created by luis rafael on 20/03/18.
 */
class KeysRecyclerAdapter(private val query: Query) : BaseAdapter<KeyData, KeysViewHolder>(firebaseOptions(query)) {

    private lateinit var interfaceKeysAdapter : InterfaceKeysAdapter

    fun setFilter(filter:String){
        startFilter()
        if (filter=="") updateOptions(firebaseOptions(query))
        else updateOptions(firebaseOptions(query,filter,"keyText"))
    }

    override fun startFilter() {
        interfaceKeysAdapter.successResult(result = false, filter = true)
    }

    override fun onDataChanged() {
        if (getSnapshots().size == 0) interfaceKeysAdapter.successResult(false)
        else interfaceKeysAdapter.successResult(true)
    }

    override fun onError(e: DatabaseError) {
        interfaceKeysAdapter.failedResult(e)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): KeysViewHolder =
            KeysViewHolder(p0.context.inflateLayout(R.layout.item_keylog, p0, false))

    override fun onBindViewHolder(holder: KeysViewHolder, position: Int, model: KeyData) =
            holder.bind(model)

    fun onRecyclerAdapterListener(interfaceKeysAdapter: InterfaceKeysAdapter){
        this.interfaceKeysAdapter = interfaceKeysAdapter
    }

}