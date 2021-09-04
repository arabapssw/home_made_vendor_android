package com.floriaapp.core.domain.model.general


import com.google.gson.annotations.SerializedName

data class GeneralDataResponse(
    @SerializedName("data")
    val `data`: GeneralItem
)

data class Categories(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class GeneralItem(
    @SerializedName("categories")
    val categories: List<Categories>,
    @SerializedName("tags")
    val tags: List<tags>
)

data class Nationality(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class bankTransferSettings(
    @SerializedName("bank") val bank: String,
    @SerializedName("account_number") val accountNumber: String
)
 class tags(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)
data class settings(
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("email") val email: String,
    @SerializedName("facebook_url") val facebook_url: String,
    @SerializedName("twitter_url") val twitter_url: String,
    @SerializedName("instagram_url") val instagram_url: String


    )