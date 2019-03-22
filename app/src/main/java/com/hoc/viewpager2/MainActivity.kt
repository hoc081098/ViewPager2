package com.hoc.viewpager2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_VERTICAL
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlin.LazyThreadSafetyMode.NONE

@ExperimentalCoroutinesApi
@ImplicitReflectionSerializer
class MainActivity : AppCompatActivity() {
    private val scope = MainScope()
    private val adapter by lazy(NONE) { ViewPagerAdapter(GlideApp.with(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.run {
            adapter = this@MainActivity.adapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    Toast.makeText(this@MainActivity, "Selected $position", Toast.LENGTH_SHORT).show()
                }
            })
            setPageTransformer(FlipTransformer(this))
        }

        GlideApp
            .with(this)
            .asGif()
            .load(R.drawable.loading_indicator)
            .into(image_loading)

        getItems()
    }

    private fun getItems() {
        scope.launch {
            image_loading.visibility = View.VISIBLE
            getViewPagerItems()
                .fold(
                    {
                        adapter.submitList(it)
                    },
                    {
                        Toast
                            .makeText(
                                this@MainActivity,
                                it.message ?: "An unexpected error occurred",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                )
            image_loading.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) = menuInflater.inflate(R.menu.menu_main, menu).let { false } //TODO

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_change_orientation) {
            view_pager.orientation = when (view_pager.orientation) {
                ORIENTATION_HORIZONTAL -> ORIENTATION_VERTICAL
                ORIENTATION_VERTICAL -> ORIENTATION_HORIZONTAL
                else -> error(":(")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
