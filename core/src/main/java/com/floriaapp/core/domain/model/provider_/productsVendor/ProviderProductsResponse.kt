package com.floriaapp.core.domain.model.provider_.productsVendor


import com.google.gson.annotations.SerializedName

data class ProviderProductsResponse(
    @SerializedName("data")
    val `data`: List<ProviderProductsResponseItem>?,
    @SerializedName("links")
    val links: Links,
    @SerializedName("meta")
    val meta: Meta
)