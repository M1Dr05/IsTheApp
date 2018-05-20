package com.github.midros.istheapp.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.support.design.widget.TabLayout
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.github.midros.istheapp.R

/**
 * Created by luis rafael on 20/03/18.
 */
class CustomTabLayout : TabLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    override fun getScrollIndicators(): Int {
        return super.getScrollIndicators()
    }


    override fun addOnTabSelectedListener(listener: TabLayout.OnTabSelectedListener) {
        val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                listener.onTabSelected(tab)
                getIndicatorTabAt(tab.position).setIndicatorVisible(false)
                if (tab.customView != null) tab.customView!!.alpha = 1f
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                listener.onTabUnselected(tab)
                if (tab.customView != null) tab.customView!!.alpha = 0.7f
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                listener.onTabReselected(tab)
            }
        }
        super.addOnTabSelectedListener(onTabSelectedListener)
    }

    override fun addTab(tab: TabLayout.Tab, position: Int, setSelected: Boolean) {
        val indicatorTab = IndicatorTab(context, tab,setSelected)
        super.addTab(indicatorTab.tabs!!, position, setSelected)
    }


    fun getIndicatorTabAt(index: Int): IndicatorTab {
        return parse(getTabAt(index))
    }

    private fun parse(tab: TabLayout.Tab?): IndicatorTab {
        return IndicatorTab.from(tab!!)
    }


    class IndicatorTab {

        var tabs: TabLayout.Tab? = null
            private set

        private var tabIcon: ImageView? = null
        private var tabCount: ImageView? = null
        private var tabText: TextView? = null

        constructor()

        constructor(context: Context, tab: TabLayout.Tab,isSelected: Boolean) {
            this.tabs = tab

            val customView = View.inflate(context, R.layout.tablayout_tab_view, null)
            init(customView)

            setSelected(customView,isSelected)

            tab.customView = customView
        }

        private fun init(customView: View) {
            tabIcon = customView.findViewById(R.id.icon_tab)
            tabCount = customView.findViewById(R.id.count_tab)
            tabText = customView.findViewById(R.id.text_tab)
        }

        private fun setSelected(customView: View,isSelected: Boolean) = if (isSelected) customView.alpha = 1f else customView.alpha = 0.7f

        fun setIndicatorVisible(visible: Boolean): IndicatorTab {
            tabCount!!.visibility = if (visible) View.VISIBLE else View.GONE
            return this
        }

        fun setIcon(icon: Int): IndicatorTab {
            tabs!!.setIcon(icon)
            tabIcon!!.background = tabs!!.icon
            return this
        }

        fun setText(text:Int) : IndicatorTab {
            tabs!!.setText(text)
            tabText!!.text = tabs!!.text
            return this
        }

        companion object {

            fun from(tab: TabLayout.Tab): IndicatorTab {
                val indicatorTab = IndicatorTab()
                indicatorTab.tabs = tab
                if (tab.customView != null) indicatorTab.init(tab.customView!!)
                return indicatorTab
            }
        }
    }
}
