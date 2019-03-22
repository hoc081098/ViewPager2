package com.hoc.viewpager2

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ViewPagerItem(
    @SerialName("image_url") val imageUrl: String,
    val name: String,
    val id: String
)