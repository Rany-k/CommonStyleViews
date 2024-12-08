package com.ranycess.commonview.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView
import com.ranycees.ranyview.core.CommonBackgroundStyleOperate
import com.ranycees.ranyview.core.CommonBackgroundStyleUnit

class MultiAutoCompleteTextViewR @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatMultiAutoCompleteTextView(context, attrs, defStyleAttr), CommonBackgroundStyleOperate {
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

    override fun setStrokeColors(vararg colors: Int) {
        commonBackgroundStyle.setStrokeColors(*colors)
    }

    override fun setBackgroundColors(vararg colors: Int) {
        commonBackgroundStyle.setBackgroundColors(*colors)
    }

    override fun setRadius(radius: Float) {
        commonBackgroundStyle.setRadius(radius)
    }

    override fun setDeviation(deviation: Int) {
        commonBackgroundStyle.setDeviation(deviation)
    }

    override fun setStrokeDeviation(deviation: Int) {
        commonBackgroundStyle.setStrokeDeviation(deviation)
    }

    override fun setBackgroundDeviation(deviation: Int) {
        commonBackgroundStyle.setBackgroundDeviation(deviation)
    }

    override fun switchStroke(isOn: Boolean) {
        commonBackgroundStyle.switchStroke(isOn)
    }

    override fun switchBackground(isOn: Boolean) {
        commonBackgroundStyle.switchBackground(isOn)
    }

}