package com.scx.customviewanimation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat

val RADIUS = 20.dp
class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var radius = RADIUS
        set(value) {
            field = value
            invalidate()
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ResourcesCompat.getColor(resources, R.color.purple_200, null)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawOval(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius, paint)
    }
}