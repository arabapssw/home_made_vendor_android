package com.floriaapp.core.domain.model.provider_.order_details


import com.google.gson.annotations.SerializedName

data class Shipping(
    @SerializedName("name")
    val name: String
)