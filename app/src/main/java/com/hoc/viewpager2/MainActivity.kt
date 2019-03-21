package com.hoc.viewpager2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.LazyThreadSafetyMode.NONE

class MainActivity : AppCompatActivity() {
    private val adapter by lazy(NONE) { ViewPagerAdapter(GlideApp.with(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.adapter = adapter

        adapter.submitList(
            listOf(
                ViewPagerItem(
                    id = "0",
                    imageUrl = "https://cdn-images-1.medium.com/max/600/1*OFJKA8dRYZSb-Kprx-VReg.png",
                    name = "Android Jetpack"
                ),
                ViewPagerItem(
                    id = "1",
                    imageUrl = "https://avatars3.githubusercontent.com/u/36917223?s=400&v=4",
                    name = "hoc081098"
                ),
                ViewPagerItem(
                    id = "2",
                    imageUrl = "https://cms-assets.tutsplus.com/uploads/users/369/posts/31577/preview_image/Building-reactive-Android-apps-with-RxJava-Kotlin.png",
                    name = "RxKotlin"
                ),
                ViewPagerItem(
                    id = "3",
                    imageUrl = "https://res.infoq.com/presentations/netflix-functional-rx/en/slides/sl74.jpg",
                    name = "Functional Reactive Programming"
                )
            )
        )
    }
}
