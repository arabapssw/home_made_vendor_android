package com.floriaapp.core.domain.model.provider_.productsVendor


import com.google.gson.annotations.SerializedName

data class ProductQuestion(
    @SerializedName("answer_ar")
    val answerAr: String,
    @SerializedName("answer_en")
    val answerEn: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("question_ar")
    val questionAr: String,
    @SerializedName("question_en")
    val questionEn: String,
    @SerializedName("status")
    val status: String
)