package com.floriaapp.core.domain.model.provider_.order_details


import com.google.gson.annotations.SerializedName

data class Nationality(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)