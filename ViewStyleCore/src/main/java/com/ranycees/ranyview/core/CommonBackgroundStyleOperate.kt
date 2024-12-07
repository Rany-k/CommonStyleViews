package com.ranycees.ranyview.core

import androidx.annotation.ColorInt
import com.ranycees.ranyview.core.CommonBackgroundStyleUnit.DEVIATION

interface CommonBackgroundStyleOperate {
    fun setStrokeWidth(width:Float)

    fun setStrokeColors(@ColorInt vararg colors: Int)

    fun setBackgroundColors(@ColorInt vararg colors: Int)

    fun setRadius(radius: Float)

    fun setDeviation(@DEVIATION deviation: Int)

    fun setStrokeDeviation(@DEVIATION deviation: Int)

    fun setBackgroundDeviation(@DEVIATION deviation: Int)

    fun switchStroke(isOn:Boolean)
    fun switchBackground(isOn: Boolean)
}