package com.floriaapp.core.domain.model.checkout.shipping.new


import com.google.gson.annotations.SerializedName

data class Total(
    @SerializedName("barq")
    val barq: Int,
    @SerializedName("streetline")
    val streetline: Int
)