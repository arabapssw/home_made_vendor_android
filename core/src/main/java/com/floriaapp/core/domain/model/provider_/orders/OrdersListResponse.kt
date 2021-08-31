package com.floriaapp.core.domain.model.provider_.orders


import com.google.gson.annotations.SerializedName

data class OrdersListResponse(
    @SerializedName("data")
    val `data`: List<OrderListResponseItem>?,
    @SerializedName("links")
    val links: Links,
    @SerializedName("meta")
    val meta: Meta
)