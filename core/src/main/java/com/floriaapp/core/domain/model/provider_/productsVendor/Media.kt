package com.floriaapp.core.domain.model.provider_.productsVendor


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Media(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String
):Parcelable