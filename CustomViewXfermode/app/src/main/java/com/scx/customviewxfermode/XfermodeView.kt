package com.scx.customviewxfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

private val WIDTH = 200f.px
private val PADDING = 20f.px
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

class XfermodeView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        // 离屏画纯正图形，方块的其余部分为透明
        val count = canvas.saveLayer(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH, paint)
        canvas.drawOval(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH, paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(getAvatar(WIDTH.toInt()), PADDING, PADDING, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
    }

    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }
}