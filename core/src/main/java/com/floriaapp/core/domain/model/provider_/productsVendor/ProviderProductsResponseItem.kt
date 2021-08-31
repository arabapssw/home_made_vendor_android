package com.floriaapp.core.domain.model.provider_.productsVendor


import com.google.gson.annotations.SerializedName

data class ProviderProductsResponseItem(
    @SerializedName("active")
    val active: Int,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("description_ar")
    val descriptionAr: String,
    @SerializedName("description_en")
    val descriptionEn: String,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("discount_end_date")
    val discountEndDate: String,
    @SerializedName("discount_start_date")
    val discountStartDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("media")
    val media: List<Media>,
    @SerializedName("name_ar")
    val nameAr: String,
    @SerializedName("name_en")
    val nameEn: String,
    @SerializedName("pinned")
    val pinned: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("product_questions")
    val productQuestions: List<ProductQuestion>,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("sku")
    val sku: String,
    @SerializedName("tags")
    val tags: List<Tag>,
    @SerializedName("url")
    val url: String,
    @SerializedName("weight")
    val weight: Int
)