package com.ranycess.commonview.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.ranycees.ranyview.core.CommonBackgroundStyleOperate
import com.ranycees.ranyview.core.CommonBackgroundStyleUnit
import androidx.annotation.ColorInt
import com.ranycees.ranyview.core.CommonBackgroundStyleUnit.DEVIATION

class ImageViewR @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatImageView(context, attrs, defStyleAttr), CommonBackgroundStyleOperate {
    private val commonBackgroundStyle = CommonBackgroundStyleUnit(this)
    private val mMatrix = Matrix()
    private val mPaintBitmap = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mShader: BitmapShader? = null
    private var currentBitmap: Bitmap? = null
    private val mBitmapRect = RectF()

    init {
        commonBackgroundStyle.initStyledAttributes(context, attrs)

        currentBitmap = getBitmap(drawable)
        onShaderChange()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        onDrawableChange()
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        onDrawableChange()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        onDrawableChange()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        if (getBitmap(drawable) != currentBitmap)
            onDrawableChange()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        onImageSizeChange(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        commonBackgroundStyle.onSizeChanged(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        commonBackgroundStyle.onBackgroundPaintDraw(canvas)

        if (currentBitmap != null) {
            canvas.drawRoundRect(
                mBitmapRect,
                commonBackgroundStyle.getRadius(),
                commonBackgroundStyle.getRadius(),
                mPaintBitmap
            )
        } else {
            super.onDraw(canvas)
        }

        commonBackgroundStyle.onStrokePaintDraw(canvas)
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

    private fun onShaderChange() {
        currentBitmap?.let {
            mShader = BitmapShader(it, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            mPaintBitmap.setShader(mShader)
        }
    }

    //边距发生变化的时候需要调用，或许可以直接拿背景
    private fun onImageSizeChange(viewWidth: Int, viewHeight: Int) {
        currentBitmap?.let { currentBitmap ->
            val width = viewWidth - paddingStart - paddingEnd
            val height = viewHeight - paddingTop - paddingBottom
            mMatrix.setScale(
                width.toFloat() / currentBitmap.width,
                height.toFloat() / currentBitmap.height
            )
            mMatrix.postTranslate(paddingStart.toFloat(),paddingTop.toFloat())
            mShader?.setLocalMatrix(mMatrix)

            mBitmapRect.set(
                paddingStart.toFloat(),
                paddingTop.toFloat(),
                viewWidth.toFloat() - paddingEnd,
                viewHeight.toFloat() - paddingBottom
            )
        }
    }

    private fun onDrawableChange() {
        mPaintBitmap?:return
        currentBitmap = getBitmap(this@ImageViewR.drawable)
        onShaderChange()
        if (width != 0 && height != 0) {
            onImageSizeChange(width, height)
            invalidate()
        }
    }

    private fun getBitmap(drawable: Drawable?): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        } else if (drawable is ColorDrawable) {
            val rect = drawable.getBounds()
            val width = rect.right - rect.left
            val height = rect.bottom - rect.top
            val color = drawable.color
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawARGB(
                Color.alpha(color),
                Color.red(color),
                Color.green(color),
                Color.blue(color)
            )
            return bitmap
        } else {
            return null
        }
    }

}