package com.scx.customviewtouchmultitouch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MultiTouchView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, 150.dp.toInt())

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var downX = 0f
    private var downY = 0f

    private var focusX = 0f
    private var focusY = 0f


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var sumX = 0f
        var sumY = 0f
        var pointerCount = event.pointerCount
        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        for (index in 0 until event.pointerCount) {
            if (!(isPointerUp && index == event.actionIndex)) {
                sumX += event.getX(index)
                sumY += event.getY(index)
            }
        }
        if (isPointerUp) pointerCount--
        focusX = sumX / pointerCount
        focusY = sumY / pointerCount

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_POINTER_UP -> {
                downX = focusX
                downY = focusY
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                offsetX = focusX - downX + originalOffsetX
                offsetY = focusY - downY + originalOffsetY
            }
        }

        invalidate()
        return true
    }
}