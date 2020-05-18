package com.bytedance.android.live.broadcast.dialog.ktv.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.guys.R

/**
 * Created by liujiannan on 2020-05-17
 */

class ToneProcessView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val MIN_TONE = 1
        private const val DEFAULT_MAX_TONE = 11
        private const val DEFAULT_STROKE_WIDTH_DIP = 2.73f
        private const val PER_HEIGHT_DIP = 1.83f
        private const val MIN_HEIGHT_DIP = 2.74f
        private const val DEFAULT_RADIUS_DIP = 3f
        private const val DEFAULT_NORMAL_COLOR = 0x29FFFFFF
        private const val DEFAULT_HIGHLIGHT_COLOR = 0xFFC421
    }

    private val highLightPaint = Paint()
    private val normalPaint = Paint()
    private val strokeWidth: Int
    private var interval: Int
    private val maxTone: Int
    private val radius: Int
    private var currentTone: Int = 3
    private val rectF: RectF = RectF()

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ToneProcessView)
        maxTone = a.getInteger(R.styleable.ToneProcessView_max_tone, DEFAULT_MAX_TONE)
        if (maxTone < MIN_TONE) {
            throw java.lang.IllegalStateException("max tone must be more than min tone($MIN_TONE)")
        }
        radius = a.getDimensionPixelSize(R.styleable.ToneProcessView_radius, dip2Px(DEFAULT_RADIUS_DIP).toInt())
        strokeWidth = a.getDimensionPixelSize(R.styleable.ToneProcessView_stroke_width, dip2Px(DEFAULT_STROKE_WIDTH_DIP).toInt())
        interval = a.getDimensionPixelSize(R.styleable.ToneProcessView_interval, -1)
        highLightPaint.color = a.getColor(R.styleable.ToneProcessView_highlight_color, DEFAULT_HIGHLIGHT_COLOR)
        highLightPaint.isDither = true
        highLightPaint.isAntiAlias = true
        highLightPaint.style = Paint.Style.FILL_AND_STROKE
        normalPaint.color = a.getColor(R.styleable.ToneProcessView_normal_color, DEFAULT_NORMAL_COLOR)
        normalPaint.isDither = true
        normalPaint.isAntiAlias = true
        normalPaint.style = Paint.Style.FILL_AND_STROKE

        a.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (interval == -1) {
            interval = (width - maxTone * strokeWidth) / (maxTone - 1)
        }

        for (i in MIN_TONE..maxTone) {
            val radius = dip2Px(DEFAULT_RADIUS_DIP)
            val startX = (i - 1) * (strokeWidth + interval).toFloat()
            rectF.left = startX
            rectF.right = startX + strokeWidth
            rectF.top = height - dip2Px(MIN_HEIGHT_DIP) - (i - 1) * dip2Px(PER_HEIGHT_DIP)
            rectF.bottom = height.toFloat()
            if (i <= currentTone) {
                canvas?.drawRoundRect(rectF,
                        radius, radius, highLightPaint)
            } else {
                canvas?.drawRoundRect(rectF,
                        radius, radius, normalPaint)
            }
        }
    }

    public fun increaseTone() {
        if (checkState(currentTone + 1)) {
            currentTone++
            invalidate()
        }
    }

    public fun decreaseTone() {
        if (checkState(currentTone - 1)) {
            currentTone--
            invalidate()
        }
    }

    public fun setTone(value: Int) {
        if(checkState(value)) {
            currentTone = value
            invalidate()
        }
    }

    private fun checkState(targetTone: Int): Boolean {
        if (targetTone < MIN_TONE || targetTone > maxTone) {
            IllegalStateException("tone must be a value in [$MIN_TONE, $maxTone] ").printStackTrace()
            return false
        }
        return true
    }

    public fun getCurrentTone(): Int {
        return currentTone
    }

    private fun dip2Px(dipValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dipValue * scale + 0.5f
    }
}