package com.floriaapp.core.domain.model.provider_.orders


import com.google.gson.annotations.SerializedName

data class Shipping(
    @SerializedName("name")
    val name: String
)