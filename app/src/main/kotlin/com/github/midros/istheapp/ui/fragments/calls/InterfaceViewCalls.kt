package com.github.midros.istheapp.ui.fragments.calls

import com.github.midros.istheapp.ui.activities.base.InterfaceView
import com.github.midros.istheapp.ui.adapters.callsadapter.CallsRecyclerAdapter

/**
 * Created by luis rafael on 12/03/18.
 */
interface InterfaceViewCalls : InterfaceView {

    fun setRecyclerAdapter(recyclerAdapter: CallsRecyclerAdapter)

}