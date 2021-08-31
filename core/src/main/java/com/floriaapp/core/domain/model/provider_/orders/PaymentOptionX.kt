package com.floriaapp.core.domain.model.provider_.orders


import com.google.gson.annotations.SerializedName

data class PaymentOptionX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)