package com.hoc.viewpager2

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.coroutines.awaitObjectResult
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import com.github.kittinunf.result.Result
import kotlinx.coroutines.delay
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.internal.ArrayListSerializer

private const val URL = "https://hoc081098.github.io/hoc081098.github.io/data.json"

@ImplicitReflectionSerializer
suspend fun getViewPagerItems(): Result<List<ViewPagerItem>, FuelError> {
    delay(2_000)

    return URL
        .httpGet()
        .awaitObjectResult(kotlinxDeserializerOf(ArrayListSerializer(ViewPagerItem.serializer())))
}