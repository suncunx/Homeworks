package com.scx.customviewtouchscalableimageview

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import kotlin.math.max
import kotlin.math.min

private val IMAGE_SIZE = 150.dp
private val OVER = 20.dp
private const val EXTRA_SCALE_FACTOR = 1.5f
private const val TAG = "ScalableImageView"

class ScalableImageView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bitmap = getAvatar(resources, IMAGE_SIZE.toInt())

    private var big = false
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var smallScale: Float = 0f
    private var bigScale: Float = 0f

    var currentScale = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val animator = ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale)

    private val simpleGestureListener = SimpleGestureListener()
    private val scaleGestureListener = ScaleGestureListener()
    private val runner = Runner()
    private val gestureDetector = GestureDetectorCompat(context, simpleGestureListener)
    private val scaleGestureDetector = ScaleGestureDetector(context, scaleGestureListener)
    private val scroller = OverScroller(context)


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.d(TAG, "onSizeChanged() called with: w = $w, h = $h, oldw = $oldw, oldh = $oldh")
        originalOffsetX = (width - bitmap.width) / 2f
        originalOffsetY = (height - bitmap.height) / 2f
        smallScale = min(width / bitmap.width.toFloat(), height / bitmap.height.toFloat())
        bigScale = max(
            width / bitmap.width.toFloat(),
            height / bitmap.height.toFloat()
        ) * EXTRA_SCALE_FACTOR
        currentScale = smallScale
        animator.setFloatValues(smallScale, bigScale)
    }

    override fun onDraw(canvas: Canvas) {

        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            gestureDetector.onTouchEvent(event)
        }
        return true
    }

    fun fixOffsets() {
        offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2f)
        offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2f)
        offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2f)
        offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2f)
    }

    inner class SimpleGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            Log.d(TAG, "onDown() called with: e.x = ${e.x} + e.y = ${e.y}")
            return true
        }

        override fun onScroll(
            downEvent: MotionEvent,
            currentEvent: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (big) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffsets()
                invalidate()
            }
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (big) {
                scroller.fling(
                    offsetX.toInt(),
                    offsetY.toInt(),
                    velocityX.toInt(),
                    velocityY.toInt(),
                    (-(bitmap.width * bigScale - width) / 2f).toInt(),
                    ((bitmap.width * bigScale - width) / 2f).toInt(),
                    (-(bitmap.height * bigScale - height) / 2f).toInt(),
                    ((bitmap.height * bigScale - height) / 2f).toInt(),
                    OVER.toInt(),
                    OVER.toInt()
                )
                ViewCompat.postOnAnimation(this@ScalableImageView, runner)
            }
            return false
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            big = !big
            if (big) {
                offsetX = (e.x - width / 2) * (1 - bigScale / smallScale)
                offsetY = (e.y - height / 2) * (1 - bigScale / smallScale)
                animator.start()
            } else {
                animator.reverse()
            }
            return true
        }
    }

    inner class ScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val temp = currentScale * detector.scaleFactor
            return if (temp in smallScale..bigScale) {
                currentScale = temp
                true
            } else {
                false
            }
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            offsetX = (detector.focusX - width / 2) * (1 - bigScale / smallScale)
            offsetY = (detector.focusY - height / 2) * (1 - bigScale / smallScale)
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
        }

    }

    inner class Runner : Runnable {
        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                ViewCompat.postOnAnimation(this@ScalableImageView, this)
            }
        }
    }
}