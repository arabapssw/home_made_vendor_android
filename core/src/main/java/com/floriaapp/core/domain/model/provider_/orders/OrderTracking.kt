package com.floriaapp.core.domain.model.provider_.orders


import com.google.gson.annotations.SerializedName

data class OrderTracking(
    @SerializedName("created_at")
    val createdAt: CreatedAtX,
    @SerializedName("id")
    val id: Int,
    @SerializedName("order_status")
    val orderStatus: String
)