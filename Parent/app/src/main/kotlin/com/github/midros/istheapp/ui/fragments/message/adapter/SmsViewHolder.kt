package com.github.midros.istheapp.ui.fragments.message.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.Sms
import kotterknife.bindView

/**
 * Created by luis rafael on 20/03/18.
 */
class SmsViewHolder(view:View) : RecyclerView.ViewHolder(view){

    val itemClick : LinearLayout by bindView(R.id.item_click_sms)
    private val address : TextView by bindView(R.id.address_item_sms)
    private val message : TextView by bindView(R.id.message_item_sms)
    private val dateTime : TextView by bindView(R.id.date_item_sms)

    fun bind(sms:Sms){
        address.text = sms.smsAddress
        message.text = sms.smsBody
        dateTime.text = sms.dateTime
    }

}