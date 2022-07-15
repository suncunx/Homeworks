package com.scx.customviewtouchmultitouch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.toColorInt

class MultiTouchView3(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4.dp
        strokeCap = Paint.Cap.ROUND
        color = "#000000".toColorInt()
    }

    private val paths = SparseArray<Path>()

    override fun onDraw(canvas: Canvas) {
        for (i in 0 until paths.size()) {
            canvas.drawPath(paths.valueAt(i), paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                val path = Path()
                path.moveTo(event.getX(actionIndex), event.getY(actionIndex))
                paths.append(pointerId, path)
            }
            MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_UP -> {
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                paths.remove(pointerId)
            }
            MotionEvent.ACTION_MOVE-> {
                for(i in 0 until paths.size()) {
                    val pointerId = event.getPointerId(i)
                    val path = paths.get(pointerId)
                    path.lineTo(event.getX(i), event.getY(i))
                }
            }
        }
        invalidate()
        return true
    }
}