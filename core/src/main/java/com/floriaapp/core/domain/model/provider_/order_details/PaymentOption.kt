package com.floriaapp.core.domain.model.provider_.order_details


import com.google.gson.annotations.SerializedName

data class PaymentOption(
    @SerializedName("key")
    val key: String,
    @SerializedName("status")
    val status: String
)