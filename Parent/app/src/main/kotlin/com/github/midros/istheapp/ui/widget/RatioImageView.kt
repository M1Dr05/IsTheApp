package com.github.midros.istheapp.ui.widget

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.github.midros.istheapp.R

/**
 * Created by luis rafael on 20/03/18.
 */
class RatioImageView @JvmOverloads constructor(context: Context,
                                               attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs, 0) {

    private var heightRatio: Float = 1.0f

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView)
        heightRatio = typedArray.getFloat(R.styleable.RatioImageView_heightRatio, 1.0f)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (heightRatio > 0.0f) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = (width * heightRatio).toInt()
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }


}