package com.hoc.viewpager2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.item_layout.view.*
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory


class ViewPagerAdapter(private val glide: GlideRequests) :
    ListAdapter<ViewPagerItem, ViewPagerAdapter.VH>(
        object : DiffUtil.ItemCallback<ViewPagerItem>() {
            override fun areItemsTheSame(oldItem: ViewPagerItem, newItem: ViewPagerItem) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: ViewPagerItem, newItem: ViewPagerItem) = oldItem == newItem
        }
    ) {
    private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_layout, parent, false)
        .let(::VH)

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.image_view!!
        private val textName = itemView.text_name!!
        fun bind(item: ViewPagerItem) {
            textName.text = item.name

            glide.load(item.imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView)
        }
    }
}