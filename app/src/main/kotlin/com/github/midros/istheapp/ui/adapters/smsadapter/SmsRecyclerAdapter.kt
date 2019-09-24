package com.github.midros.istheapp.ui.adapters.smsadapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.Sms
import com.github.midros.istheapp.ui.adapters.basedapter.BaseAdapter
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
class SmsRecyclerAdapter(private val query: Query) : BaseAdapter<Sms, SmsViewHolder>(firebaseOptions(query)) {

    private lateinit var interfaceSmsAdapter: InterfaceSmsAdapter

    fun setFilter(filter:String){
        startFilter()
        if (filter=="") updateOptions(firebaseOptions(query))
        else updateOptions(firebaseOptions(query,filter,"smsAddress","smsBody"))
    }

    override fun startFilter() {
        interfaceSmsAdapter.successResult(result = false,filter = true)
    }

    override fun onDataChanged() {
        if (getSnapshots().size == 0) interfaceSmsAdapter.successResult(false)
        else interfaceSmsAdapter.successResult(true)
    }

    override fun onError(e: DatabaseError) {
        interfaceSmsAdapter.failedResult(e)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SmsViewHolder =
            SmsViewHolder(p0.context.inflateLayout(R.layout.item_sms))

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: SmsViewHolder, position: Int, model: Sms) {
        holder.bind(model)
        val key = getRef(position).key
        holder.isSelected(key!!, model.type!!)

        RxView.clicks(holder.itemClick).subscribe({
            interfaceSmsAdapter.onItemClick(key,position)
        },{ e(TAG, it.message.toString()) })

        RxView.longClicks(holder.itemClick).subscribe({
            interfaceSmsAdapter.onItemLongClick(key,position)
        }, { e(TAG, it.message.toString()) })
    }

    fun onRecyclerAdapterListener(interfaceSmsAdapter: InterfaceSmsAdapter) {
        this.interfaceSmsAdapter = interfaceSmsAdapter
    }

}