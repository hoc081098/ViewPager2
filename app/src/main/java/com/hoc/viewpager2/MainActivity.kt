package com.hoc.viewpager2

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
                    imageUrl = "https://avatars3.githubusercontent.com/u/36917223?s=400&v=4",
                    name = "hoc081098 [1]"
                ),
                ViewPagerItem(
                    id = "1",
                    imageUrl = "https://avatars3.githubusercontent.com/u/36917223?s=400&v=4",
                    name = "hoc081098 [2]"
                )
            )
        )
    }
}
