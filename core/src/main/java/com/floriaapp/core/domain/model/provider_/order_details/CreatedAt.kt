package com.floriaapp.core.domain.model.provider_.order_details


import com.google.gson.annotations.SerializedName

data class CreatedAt(
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String
)