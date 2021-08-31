package com.floriaapp.core.domain.model.provider_.order_details


import com.google.gson.annotations.SerializedName

data class District(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)