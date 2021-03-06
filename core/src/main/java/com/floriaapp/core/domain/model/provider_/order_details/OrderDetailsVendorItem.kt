package com.floriaapp.core.domain.model.provider_.order_details


import com.google.gson.annotations.SerializedName

data class OrderDetailsVendorItem(
    @SerializedName("address")
    val address: Address,
    @SerializedName("created_at")
    val createdAt: CreatedAt,
    @SerializedName("discount")
    val discount: Double,
    @SerializedName("has_rating")
    val hasRating: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("order_id")
    val orderId: Int,
    @SerializedName("order_status")
    val orderStatus: OrderStatus,
    @SerializedName("order_trackings")
    val orderTrackings: List<OrderTracking>,
    @SerializedName("payment_option")
    val paymentOption: PaymentOption,
    @SerializedName("payment_options")
    val paymentOptions: List<Any>,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("rating")
    val rating: Any,
    @SerializedName("required_at")
    val requiredAt: RequiredAt,
    @SerializedName("shipping")
    val shipping: Shipping,
    @SerializedName("shipping_fees")
    val shippingFees: Double,
    @SerializedName("subtotal")
    val subtotal: Double,
    @SerializedName("subtotal_after_discount")
    val subtotalAfterDiscount: Double,
    @SerializedName("total")
    val total: Double,
    @SerializedName("total_tax")
    val totalTax: Double,
    @SerializedName("user")
    val user: User
)