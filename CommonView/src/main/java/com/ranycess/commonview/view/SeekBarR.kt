package com.ranycess.commonview.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import com.ranycees.ranyview.core.CommonBackgroundStyleOperate
import com.ranycees.ranyview.core.CommonBackgroundStyleUnit
import androidx.annotation.ColorInt
import com.ranycees.ranyview.core.CommonBackgroundStyleUnit.DEVIATION

class SeekBarR @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatSeekBar(context, attrs, defStyleAttr), CommonBackgroundStyleOperate {
    private val commonBackgroundStyle = CommonBackgroundStyleUnit(this)
    init {
        commonBackgroundStyle.initStyledAttributes(context,attrs)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        commonBackgroundStyle.onSizeChanged(w,h)
    }

    override fun onDraw(canvas: Canvas) {
        commonBackgroundStyle.onPaintDraw(canvas)
        super.onDraw(canvas)
    }

    override fun setStrokeWidth(width: Float) {
        commonBackgroundStyle.setStrokeWidth(width)
    }

    override fun setStrokeColors(@ColorInt vararg colors: Int) {
        commonBackgroundStyle.setStrokeColors(*colors)
    }

    override fun setBackgroundColors(@ColorInt vararg colors: Int) {
        commonBackgroundStyle.setBackgroundColors(*colors)
    }

    override fun setRadius(radius: Float) {
        commonBackgroundStyle.setRadius(radius)
    }

    override fun setDeviation(@DEVIATION deviation: Int) {
        commonBackgroundStyle.setDeviation(deviation)
    }

    override fun setStrokeDeviation(@DEVIATION deviation: Int) {
        commonBackgroundStyle.setStrokeDeviation(deviation)
    }

    override fun setBackgroundDeviation(@DEVIATION deviation: Int) {
        commonBackgroundStyle.setBackgroundDeviation(deviation)
    }

    override fun switchStroke(isOn: Boolean) {
        commonBackgroundStyle.switchStroke(isOn)
    }

    override fun switchBackground(isOn: Boolean) {
        commonBackgroundStyle.switchBackground(isOn)
    }

}