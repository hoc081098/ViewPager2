package com.hoc.viewpager2

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class FlipTransformer(private val viewPager: ViewPager2) : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val rotation = -180f * position

        page.visibility = if (rotation > 90f || rotation < -90f) View.INVISIBLE else View.VISIBLE
        page.pivotX = page.width * 0.5f
        page.pivotY = page.height * 0.5f

        when (viewPager.orientation) {
            ViewPager2.ORIENTATION_VERTICAL -> page.rotationX = rotation
            ViewPager2.ORIENTATION_HORIZONTAL -> page.rotationY = rotation
        }
    }
}