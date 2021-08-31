package com.floriaapp.core.domain.model.provider_.order_details


import com.google.gson.annotations.SerializedName

data class OrderDetailsVendorResponse(
    @SerializedName("data")
    val `data`: OrderDetailsVendorItem
)