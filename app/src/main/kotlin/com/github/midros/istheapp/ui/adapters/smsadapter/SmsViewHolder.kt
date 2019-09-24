package com.github.midros.istheapp.ui.adapters.smsadapter

import androidx.core.content.ContextCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.Sms
import com.github.midros.istheapp.data.preference.DataSharePreference.getSelectedItem
import com.github.midros.istheapp.utils.Consts
import kotterknife.bindView

/**
 * Created by luis rafael on 20/03/18.
 */
class SmsViewHolder(view:View) : RecyclerView.ViewHolder(view){

    val itemClick : LinearLayout by bindView(R.id.item_click_sms)
    private val address : TextView by bindView(R.id.address_item_sms)
    private val message : TextView by bindView(R.id.message_item_sms)
    private val dateTime : TextView by bindView(R.id.date_item_sms)
    private val typeSms : ImageView by bindView(R.id.img_type_sms)
    private val card : CardView by bindView(R.id.cardview_sms)

    fun isSelected(key:String,type:Int){
        if (itemView.context.getSelectedItem(key)){
            card.setCardBackgroundColor(ContextCompat.getColor(itemView.context,R.color.colorSelected))
            typeSms.setImageResource(R.drawable.ic_check)
        }else{
            if (type== Consts.TYPE_SMS_OUTGOING) typeSms.setImageResource(R.drawable.ic_made_green_24dp)
            if (type== Consts.TYPE_SMS_INCOMING) typeSms.setImageResource(R.drawable.ic_received_blue_24dp)
            card.setCardBackgroundColor(ContextCompat.getColor(itemView.context,R.color.colorWhite))
        }
    }

    fun bind(sms:Sms){
        address.text = sms.smsAddress
        message.text = sms.smsBody
        dateTime.text = sms.dateTime
    }

}