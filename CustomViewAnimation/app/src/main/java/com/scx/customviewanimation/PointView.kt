package com.scx.customviewanimation

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat

class PointView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var point = PointF(0f, 0f)
        set(value) {
            field = value
            invalidate()
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ResourcesCompat.getColor(resources, R.color.black, null)
        strokeWidth = 20.dp
        strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPoint(point.x, point.y,  paint)
    }

    class PointFEvaluator : TypeEvaluator<PointF> {
        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
            val startX = startValue.x;
            val startY = startValue.y;
            val endX = endValue.x;
            val endY = endValue.y;
            val currentX = startX + (endX - startX) * fraction
            val currentY = startY + (endY - startY) * fraction
            return PointF(currentX, currentY)
        }

    }
}