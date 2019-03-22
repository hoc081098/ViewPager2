package com.hoc.viewpager2

import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import kotlinx.android.synthetic.main.item_layout.view.*

class ViewPagerAdapter(private val glide: GlideRequests) :
    ListAdapter<ViewPagerItem, ViewPagerAdapter.VH>(
        object : DiffUtil.ItemCallback<ViewPagerItem>() {
            override fun areItemsTheSame(oldItem: ViewPagerItem, newItem: ViewPagerItem) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: ViewPagerItem, newItem: ViewPagerItem) = oldItem == newItem
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_layout, parent, false)
        .let(::VH)

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.image_view!!
        private val textName = itemView.text_name!!

        fun bind(item: ViewPagerItem) {
            textName.text = item.name

            val circularProgressDrawable = CircularProgressDrawable(itemView.context)
                .apply {
                    strokeWidth = 5f
                    centerRadius = 30f
                    setColorSchemeColors(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorAccent
                        ),
                        Color.BLACK
                    )
                    start()
                }

            glide
                .asBitmap()
                .load(item.imageUrl)
                .transition(BitmapTransitionOptions.withCrossFade(factory))
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_image_accent_24dp)
                .listener(object : RequestListener<Bitmap?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap?>?,
                        isFirstResource: Boolean
                    ): Boolean = false

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        resource ?: return false

                        Palette
                            .from(resource)
                            .generate {
                                when (val darkVibrantSwatch = it?.darkVibrantSwatch) {
                                    null -> {
                                        itemView.setBackgroundColor(Color.parseColor("#FEFEFE"))
                                        textName.setTextColor(Color.parseColor("#212121"))
                                    }
                                    else -> {
                                        itemView.setBackgroundColor(darkVibrantSwatch.rgb)
                                        textName.setTextColor(darkVibrantSwatch.titleTextColor)
                                    }
                                }
                            }

                        return false
                    }
                })
                .into(imageView)
        }
    }

    companion object {
        private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()!!
    }
}