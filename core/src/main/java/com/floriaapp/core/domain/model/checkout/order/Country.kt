package com.floriaapp.core.domain.model.checkout.order


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)