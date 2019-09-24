package com.github.midros.istheapp.ui.fragments.message

import com.github.midros.istheapp.ui.activities.base.InterfaceView
import com.github.midros.istheapp.ui.adapters.smsadapter.SmsRecyclerAdapter

/**
 * Created by luis rafael on 20/03/18.
 */
interface InterfaceViewMessage : InterfaceView {

    fun setRecyclerAdapter(smsRecyclerAdapter: SmsRecyclerAdapter)

}