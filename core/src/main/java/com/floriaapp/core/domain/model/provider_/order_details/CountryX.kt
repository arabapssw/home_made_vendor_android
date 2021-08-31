package com.floriaapp.core.domain.model.provider_.order_details


import com.google.gson.annotations.SerializedName

data class CountryX(
    @SerializedName("dial_code")
    val dialCode: String,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("iso")
    val iso: String,
    @SerializedName("name")
    val name: String
)