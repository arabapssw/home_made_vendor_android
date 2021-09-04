package com.floriaapp.core.domain.model.provider_.productsVendor


import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)