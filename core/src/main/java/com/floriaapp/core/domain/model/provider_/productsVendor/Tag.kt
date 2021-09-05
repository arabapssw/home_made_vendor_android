package com.floriaapp.core.domain.model.provider_.productsVendor


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
):Parcelable