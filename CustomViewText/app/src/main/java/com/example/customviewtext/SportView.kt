package com.example.customviewtext

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat

val RADIUS = 150.dp
private const val TEXT = "abab"
private const val TAG = "SportView"

fun Paint.FontMetrics.toMyString() = "top = ${this.top}, ascent = ${this.ascent}, descent = ${this.descent}, bottom = ${this.bottom}, leading = ${this.leading}, "
class SportView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 10.dp
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 100.dp
        color = ResourcesCompat.getColor(resources, R.color.teal_700, null)
        textAlign = Paint.Align.CENTER
    }

    private val fontMetrics = Paint.FontMetrics()

    private val bounds = Rect()

    override fun onDraw(canvas: Canvas) {
        // 圆
        paint.color = ResourcesCompat.getColor(resources, R.color.purple_200, null)
        canvas.drawArc(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS,
        0f, 360f, false, paint)

        // 弧
        paint.color = ResourcesCompat.getColor(resources, R.color.teal_200, null)
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS,
        -60f, 200f, false, paint)

        // 字
        textPaint.getFontMetrics(fontMetrics)
        Log.d(TAG, "onDraw: ${fontMetrics.toMyString()}")
        textPaint.getTextBounds(TEXT, 0, TEXT.length, bounds)
        Log.d(TAG, "onDraw: $bounds")
        canvas.drawText(TEXT, width / 2f, height / 2f - (fontMetrics.descent + fontMetrics.ascent) / 2, textPaint)
    }

}