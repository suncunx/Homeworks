package com.example.customviewtext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat

val IMAGE_WIDTH = 150.dp
val IMAGE_PADDING = 50.dp
private const val TAG = "MultilineView"
private val TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce quis nunc diam. In gravida sed velit sit amet egestas. Integer eget enim vitae est interdum dictum et id nibh. In a malesuada mauris. Ut blandit augue id justo ullamcorper feugiat. Nulla nec consectetur est. Aenean ultrices egestas sem, vitae tristique dui luctus nec. Sed lacinia viverra libero sit amet eleifend. Quisque semper tincidunt posuere.\n" +
        "\n" +
        "Sed ultricies purus vel augue porta ornare. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer fringilla, arcu ut ultrices mollis, enim est scelerisque tortor, nec feugiat tellus ex vitae mi. Integer nec massa vitae lacus eleifend mollis hendrerit a nisl. In nibh sapien, semper id mi porttitor, malesuada tempor ligula. Curabitur ac gravida purus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hac habitasse platea dictumst. Nullam a sodales mauris. Vestibulum sem sem, sagittis auctor vestibulum in, tempus at metus. Aenean faucibus quam at dolor sollicitudin tempor. Vestibulum et consequat lorem. Sed bibendum libero ac lobortis dapibus. Maecenas maximus id ipsum vitae venenatis. Duis rutrum dapibus lacus nec iaculis. Aenean sit amet nibh vel dui dictum pellentesque.\n" +
        "\n" +
        "Nunc nulla dui, scelerisque sit amet eros a, tristique dignissim urna. Praesent nulla eros, pellentesque a bibendum id, hendrerit sagittis eros. Nam ac eros vitae dolor suscipit varius. Ut ornare neque non risus sollicitudin, nec mollis nunc suscipit. Sed ac tellus quis arcu hendrerit dictum. Praesent venenatis vel dui eget tincidunt. Sed ex ex, hendrerit ut euismod quis, facilisis ut odio. Duis dictum, ante eget gravida ornare, nisl ligula vulputate ligula, eu luctus nulla massa et sapien. Integer sit amet semper magna, et feugiat lacus. Nulla porttitor nisl eu sodales tempor. Maecenas sollicitudin feugiat odio, nec dapibus dui tempus eu. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Maecenas semper ligula nec vulputate bibendum. Integer at posuere massa.\n" +
        "\n" +
        "Curabitur posuere posuere justo sed sagittis. Fusce nec erat eu neque dictum consequat id vitae risus. Integer libero ante, eleifend porttitor pretium eget, molestie quis magna. Morbi aliquam ligula quis nisi vestibulum imperdiet. Fusce a efficitur ipsum. Pellentesque bibendum, neque et tristique congue, magna purus rutrum ante, nec euismod mauris urna sed mi. Cras ac tortor in tellus tincidunt pulvinar eu in lacus.\n" +
        "\n" +
        "Proin vitae eleifend ante. Pellentesque lectus arcu, gravida bibendum mi nec, hendrerit rhoncus elit. Sed vestibulum ac quam quis consequat. Ut tortor nibh, vehicula eu nibh sit amet, euismod accumsan turpis. Curabitur sollicitudin nisi a lacus ornare, eu rhoncus ipsum fermentum. Vivamus non libero varius, eleifend massa sit amet, lacinia odio. Donec vel velit convallis, laoreet massa in, suscipit sapien."

class MultilineView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp
        color = ResourcesCompat.getColor(resources, R.color.teal_700, null)
    }

    private val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(getAvatar(IMAGE_WIDTH.toInt()), width - IMAGE_WIDTH, IMAGE_PADDING, paint)

        textPaint.getFontMetrics(fontMetrics)

        var start = 0
        var count: Int
        var verticalOffset = -fontMetrics.top
        var maxWidth: Float
        val measureWidth = floatArrayOf(0f)
        Log.d(TAG, "onDraw: textPaint.fontSpacing = ${textPaint.fontSpacing}, fontMetrics = ${fontMetrics.toMyString()}")
        while (start < TEXT.length) {
            if (verticalOffset + fontMetrics.bottom < IMAGE_PADDING ||
                    verticalOffset + fontMetrics.top > IMAGE_PADDING + IMAGE_WIDTH) {
                maxWidth = width.toFloat()
            } else {
                maxWidth = width.toFloat() - IMAGE_WIDTH
            }
            count = textPaint.breakText(TEXT, start, TEXT.length, true, maxWidth, measureWidth)
            canvas.drawText(TEXT, start, start + count, 0f, verticalOffset, textPaint)
            start += count
            verticalOffset += textPaint.fontSpacing
        }
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