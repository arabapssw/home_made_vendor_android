package com.floriaapp.core.domain.model.provider_.orders


import com.google.gson.annotations.SerializedName

data class CreatedAtX(
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String
)