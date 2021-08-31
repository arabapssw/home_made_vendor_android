package com.floriaapp.core.domain.model.provider_.productsVendor


import com.google.gson.annotations.SerializedName

data class Media(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String
)