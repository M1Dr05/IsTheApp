package com.github.midros.istheapp.ui.fragments.message.adapter

import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.Sms
import com.github.midros.istheapp.utils.ConstFun.firebaseOptions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.github.midros.istheapp.utils.Consts.TAG
import com.jakewharton.rxbinding2.view.RxView
import com.pawegio.kandroid.e
import com.pawegio.kandroid.inflateLayout

/**
 * Created by luis rafael on 20/03/18.
 */
class SmsRecyclerAdapter(query: Query) : FirebaseRecyclerAdapter<Sms, SmsViewHolder>(firebaseOptions(query)) {

    private lateinit var interfaceSmsAdapter: InterfaceSmsAdapter

    override fun onDataChanged() {
        if (snapshots.size == 0) interfaceSmsAdapter.successResult(false)
        else interfaceSmsAdapter.successResult(true)
    }

    override fun onError(error: DatabaseError) {
        interfaceSmsAdapter.failedResult(error)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsViewHolder =
            SmsViewHolder(parent.context.inflateLayout(R.layout.item_sms))

    override fun onBindViewHolder(holder: SmsViewHolder, position: Int, model: Sms) {
        holder.bind(model)
        val key = getRef(position).key

        RxView.longClicks(holder.itemClick).subscribe({
            interfaceSmsAdapter.onLongClickSms(key)
        }, { e(TAG, it.message.toString()) })

    }

    fun onRecyclerAdapterListener(interfaceSmsAdapter: InterfaceSmsAdapter) {
        this.interfaceSmsAdapter = interfaceSmsAdapter
    }

}