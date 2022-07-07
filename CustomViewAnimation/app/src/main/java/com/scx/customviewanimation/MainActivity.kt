package com.scx.customviewanimation

import android.animation.ObjectAnimator
import android.animation.PointFEvaluator
import android.animation.PropertyValuesHolder
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = findViewById<View>(R.id.view)

        /*val animator = ObjectAnimator.ofFloat(view, "radius", 80.dp)
        animator.startDelay = 2000
        animator.duration = 1000
        animator.start()*/

        /*val animator = ObjectAnimator.ofObject(view, "point", PointFEvaluator(), PointF(100.dp, 100.dp))
        animator.startDelay = 2000
        animator.duration = 1000
        animator.start()*/

        /*val animator = ObjectAnimator.ofObject(view, "province", ProvinceView.ProvinceEvaluator(), "澳门特别行政区")
        animator.startDelay = 2000
        animator.duration = 5000
        animator.start()*/

        val topFlipValueHolder = PropertyValuesHolder.ofFloat("topFlip", -60f)
        val bottomFlipValueHolder = PropertyValuesHolder.ofFloat("bottomFlip", 60f)
        val flipRotationValueHolder = PropertyValuesHolder.ofFloat("flipRotation", 270f)

        val holderAnimator = ObjectAnimator.ofPropertyValuesHolder(view, topFlipValueHolder, bottomFlipValueHolder, flipRotationValueHolder)
        holderAnimator.startDelay = 2000
        holderAnimator.duration = 3000
        holderAnimator.start()

    }
}