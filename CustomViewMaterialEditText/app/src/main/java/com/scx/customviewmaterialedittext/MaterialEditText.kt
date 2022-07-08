package com.scx.customviewmaterialedittext

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.graphics.toColorInt

val TEXT_SIZE = 12.dp
val TEXT_MARGIN = 8.dp
val VERTICAL_OFFSET = 2.dp
val EXTRA_VERTICAL_OFFSET = 12.dp
private const val TAG = "MaterialEditText"
class MaterialEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = TEXT_SIZE
        color = "#30ff0000".toColorInt()
    }

    private var floatingLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    var useFloatingLabel = false
        set(value) {
            if (field != value) {
                field = value
                if (field) {
                    setPadding(paddingLeft, (paddingTop + TEXT_MARGIN + TEXT_SIZE).toInt(), paddingRight, paddingBottom)
                } else {
                    setPadding(paddingLeft, (paddingTop - TEXT_MARGIN - TEXT_SIZE).toInt(), paddingRight, paddingBottom)
                }
            }
        }

    private val animator = ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f, 1f)

    private var floatingLabelShown = false

    init {
        val typedArray = context.obtainStyledAttributes(attrs, intArrayOf(R.attr.useFloatingLabel))
        Log.d(TAG, "typedArrayCount: ${typedArray.indexCount}")
        useFloatingLabel = typedArray.getBoolean(0, true)
        typedArray.recycle()
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (useFloatingLabel && !floatingLabelShown && !text.isNullOrEmpty()) {
            floatingLabelShown = true
            animator.start()
        } else if (useFloatingLabel && floatingLabelShown && text.isNullOrEmpty()) {
            floatingLabelShown = false
            animator.reverse()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.alpha = (floatingLabelFraction * (0xff)).toInt()
        val verticalOffset = TEXT_MARGIN + TEXT_SIZE + EXTRA_VERTICAL_OFFSET * (1 - floatingLabelFraction)
        canvas.drawText(hint.toString(), paddingLeft.toFloat(), verticalOffset, paint)
    }
}