package com.floriaapp.core.domain.model.summary


import com.google.gson.annotations.SerializedName



data class SummaryOrderResponse(
    @SerializedName("data")
    var data:SummaryOrder
)
data class SummaryOrder(
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("shipping_fees")
    val shippingFees: Int,
    @SerializedName("subtotal")
    val subtotal: Int,
    @SerializedName("subtotal_after_discount")
    val subtotalAfterDiscount: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_tax")
    val totalTax: Int
)