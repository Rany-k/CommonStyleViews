package com.ranycees.ranyview.core

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.annotation.RestrictTo

class CommonBackgroundStyleUnit(private val targetView: View):CommonBackgroundStyleOperate {
    private val TAG = "CommonBackgroundStyleUnit"

    companion object{
        const val DEVIATION_VERTICAL = 0
        const val DEVIATION_HORIZONTAL = 1
        const val DEVIATION_SLANTING_DOWN = 2
        const val DEVIATION_SLANTING_TOP = 3
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
    @IntDef(DEVIATION_VERTICAL, DEVIATION_HORIZONTAL, DEVIATION_SLANTING_DOWN, DEVIATION_SLANTING_TOP)
    @Retention(
        AnnotationRetention.SOURCE
    )
    annotation class DEVIATION


    private var hasStroke = false
    private var strokePaint:Paint? = null
    private var strokeColors:IntArray? = null
    private var strokeDeviation = DEVIATION_HORIZONTAL
    private var strokeWidth = 0f

    private var hasFill = false
    private var fillPaint:Paint? = null
    private var fillColors:IntArray? = null
    private var fillDeviation = DEVIATION_HORIZONTAL

    private var paintRadius = 0f
    private var drawRect:RectF? = null




    fun initStyledAttributes(context: Context, attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonBackgroundStyle)
        initStyledAttributes(typedArray)
        typedArray.recycle()
    }

    fun initStyledAttributes(typedArray: TypedArray){

        typedArray.getInt(R.styleable.CommonBackgroundStyle_deviation,-1).let {
            if (it != -1){
                strokeDeviation = it
                fillDeviation = it
            }
        }

        strokeWidth = typedArray.getDimension(R.styleable.CommonBackgroundStyle_stroke_width,0f)
        val strokeColorsString = typedArray.getString(R.styleable.CommonBackgroundStyle_stroke_colors)
        strokeColors = encodeColors(strokeColorsString)
        hasStroke = typedArray.getBoolean(R.styleable.CommonBackgroundStyle_stroke_alive,false) ||strokeColors?.isNotEmpty()==true
        strokeDeviation = typedArray.getInt(R.styleable.CommonBackgroundStyle_stroke_deviation,strokeDeviation)

        val fillColorsString = typedArray.getString(R.styleable.CommonBackgroundStyle_background_colors)
        fillColors = encodeColors(fillColorsString)
        hasFill= typedArray.getBoolean(R.styleable.CommonBackgroundStyle_background_alive,false) ||fillColors?.isNotEmpty()==true
        fillDeviation = typedArray.getInt(R.styleable.CommonBackgroundStyle_background_deviation,strokeDeviation)


        paintRadius = typedArray.getDimension(R.styleable.CommonBackgroundStyle_background_radius,paintRadius)

    }

    fun onSizeChanged(width: Int, height: Int) {
        val widthF = width.toFloat()
        val heightF = height.toFloat()

        buildStrokeShader(widthF, heightF)
        buildFillShader(widthF, heightF)

        drawRect = RectF(
            (strokePaint?.strokeWidth ?: 0f) / 2,
            (strokePaint?.strokeWidth ?: 0f) / 2,
            widthF - (strokePaint?.strokeWidth ?: 0f) / 2,
            heightF - (strokePaint?.strokeWidth ?: 0f) / 2
        )
    }

    fun onPaintDraw(canvas: Canvas) {
        onBackgroundPaintDraw(canvas)
        onStrokePaintDraw(canvas)
    }

    fun onBackgroundPaintDraw(canvas: Canvas){
        if (fillPaint != null && drawRect != null) {
            canvas.drawRoundRect(drawRect!!, paintRadius, paintRadius, fillPaint!!)
        }
    }

    fun onStrokePaintDraw(canvas: Canvas){
        if (strokePaint != null && drawRect != null) {
            canvas.drawRoundRect(drawRect!!, paintRadius, paintRadius, strokePaint!!)
        }
    }

    private fun buildStrokeShader(width: Float?, height: Float?) {
        if (!hasStroke) return
        width ?: return
        height ?: return
        if (width == 0f || height == 0f) return

        this.strokeColors?.let { targetColors ->
            strokePaint = Paint().apply {
                this.style = Paint.Style.STROKE
                this.isAntiAlias = true
                this.strokeWidth = strokeWidth
            }

            strokePaint?.shader = when (strokeDeviation) {
                DEVIATION_VERTICAL -> {
                    LinearGradient(0f, 0f, 0f, height, targetColors, null, Shader.TileMode.CLAMP)
                }

                DEVIATION_HORIZONTAL -> {
                    LinearGradient(0f, 0f, width, 0f, targetColors, null, Shader.TileMode.CLAMP)
                }

                DEVIATION_SLANTING_DOWN -> {
                    LinearGradient(0f, 0f, width, height, targetColors, null, Shader.TileMode.CLAMP)
                }

                DEVIATION_SLANTING_TOP -> {
                    LinearGradient(0f, height, width, 0f, targetColors, null, Shader.TileMode.CLAMP)
                }

                else -> {
                    LinearGradient(0f, 0f, width, 0f, targetColors, null, Shader.TileMode.CLAMP)
                }
            }
        }
    }

    private fun buildFillShader(width: Float?, height: Float?) {
        if (!hasFill) {
            fillPaint = null
            return
        }
        width ?: return
        height ?: return
        if (width == 0f || height == 0f) return

        this.fillColors?.let { targetColors ->
            fillPaint = Paint().apply {
                this.style = Paint.Style.FILL
                this.isAntiAlias = true
            }

            fillPaint?.shader = when (strokeDeviation) {
                DEVIATION_VERTICAL -> {

                    LinearGradient(0f, 0f, 0f, height, targetColors, null, Shader.TileMode.CLAMP)
                }

                DEVIATION_HORIZONTAL -> {
                    LinearGradient(0f, 0f, width, 0f, targetColors, null, Shader.TileMode.CLAMP)
                }

                DEVIATION_SLANTING_DOWN -> {
                    LinearGradient(0f, 0f, width, height, targetColors, null, Shader.TileMode.CLAMP)
                }

                DEVIATION_SLANTING_TOP -> {
                    LinearGradient(0f, height, width, 0f, targetColors, null, Shader.TileMode.CLAMP)
                }

                else -> {
                    LinearGradient(0f, 0f, width, 0f, targetColors, null, Shader.TileMode.CLAMP)
                }
            }
        }
    }

    private fun encodeColors(colorsString: String?): IntArray? {
        if (!colorsString.isNullOrEmpty()) {
            try {
                val colorTextArray = colorsString.split(",")
                return colorTextArray.map { Color.parseColor(it) }.toIntArray()
            } catch (e: Exception) {
                Log.e(TAG, "decode color's style error!! check all color is legal on current .xml")
            }
        }
        return null
    }

    //希望被委托的
    override fun setStrokeWidth(width:Float) {
        this.strokeWidth = width
        buildStrokeShader(targetView.width.toFloat(), targetView.height.toFloat())
        targetView.invalidate()
    }

    override fun setStrokeColors(@ColorInt vararg colors: Int) {
        this.strokeColors = colors
        buildStrokeShader(targetView.width.toFloat(), targetView.height.toFloat())
        targetView.invalidate()
    }

    override fun setBackgroundColors(@ColorInt vararg colors: Int) {
        this.fillColors = colors
        buildFillShader(targetView.width.toFloat(), targetView.height.toFloat())
        targetView.invalidate()
    }

    override fun setRadius(radius: Float) {
        paintRadius = radius
        buildStrokeShader(targetView.width.toFloat(), targetView.height.toFloat())
        buildFillShader(targetView.width.toFloat(), targetView.height.toFloat())
        targetView.invalidate()
    }

    fun getRadius():Float{
        return paintRadius
    }

    override fun setDeviation(@DEVIATION deviation: Int) {
        this.strokeDeviation = deviation
        this.fillDeviation = deviation
        buildStrokeShader(targetView.width.toFloat(), targetView.height.toFloat())
        buildFillShader(targetView.width.toFloat(), targetView.height.toFloat())
        targetView.invalidate()
    }

    override fun setStrokeDeviation(@DEVIATION deviation: Int) {
        this.strokeDeviation = deviation
        buildStrokeShader(targetView.width.toFloat(), targetView.height.toFloat())
        targetView.invalidate()
    }

    override fun setBackgroundDeviation(@DEVIATION deviation: Int) {
        this.fillDeviation = deviation
        buildFillShader(targetView.width.toFloat(), targetView.height.toFloat())
        targetView.invalidate()
    }

    override fun switchStroke(isOn:Boolean){
        hasStroke = isOn
        buildStrokeShader(targetView.width.toFloat(), targetView.height.toFloat())
        targetView.invalidate()
    }
    override fun switchBackground(isOn: Boolean){
        hasFill = isOn
        buildFillShader(targetView.width.toFloat(), targetView.height.toFloat())
        targetView.invalidate()
    }

}