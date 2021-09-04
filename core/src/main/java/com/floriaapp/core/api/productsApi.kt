package com.floriaapp.core.api

import com.floriaapp.core.domain.model.category.CategoriesResponse
import com.floriaapp.core.domain.model.category.CategoryProducts
import com.floriaapp.core.domain.model.orderSuccess.OrderSuccess
import com.floriaapp.core.domain.model.product.ProductDetailsResponse
import com.floriaapp.core.domain.model.provider_.order_details.OrderDetailsVendorResponse
import com.floriaapp.core.domain.model.provider_.orders.OrdersListResponse
import com.floriaapp.core.domain.model.provider_.productsVendor.ProviderProductsResponse
import com.floriaapp.core.domain.model.questions.ProductQuestions
import com.floriaapp.core.domain.model.rating.RatingData
import com.floriaapp.core.domain.model.success.SuccessMessage
import com.floriaapp.core.domain.model.tagItems.TageItemResponse
import com.floriaapp.core.domain.model.tagItems.TagsProducts
import retrofit2.http.*

interface productsApi {

    @GET("/api/v1/categories")
    suspend fun getCategories(): CategoriesResponse

    @GET("/api/v1/categories/{category_id}")
    suspend fun getCategoryProducts(
        @Path("category_id") categoryId: Int?,
        @Query("page") pageNumber: Int
    ): CategoryProducts

    @GET("/api/v1/products/{product_id}")
    suspend fun getProductDetails(@Path("product_id") productId: Int): ProductDetailsResponse


    @GET("/api/v1/order-ratings")
    suspend fun getRatings(@Query("page") pageNumber: Int): RatingData

    @GET("/api/v1/featured-products")
    suspend fun getFeautredProducts(@Query("page") pageNumber: Int): CategoryProducts

    @GET("/api/v1/favorites")
    suspend fun getFavourites(@Query("page") pageNumber: Int): CategoryProducts

    @GET("/api/v1/search")
    suspend fun search(
        @Query("search") searchWord: String,
        @Query("page") pageNumber: Int
    ): CategoryProducts

    @POST("/api/v1/products/{product_id}/favorites")
    suspend fun addToFavourites(@Path("product_id") product_id: Int): OrderSuccess

    @DELETE("/api/v1/products/{product_id}/favorites")
    suspend fun deleteFromFavourites(@Path("product_id") product_id: Int): OrderSuccess


    @GET("/api/v1/provider/products")
    suspend fun getAllProducts(@Query("page") pageNumber: Int): ProviderProductsResponse


    @GET("/api/v1/tags/{tagID}")
    suspend fun getTagProducts(@Path("tagID") tagID: Int): TagsProducts


    @GET("/api/v1/product-questions")
    suspend fun getQuestionsProducts(@Query("page") pageNumber: Int): ProductQuestions

    @POST("/api/v1/product-questions")
    suspend fun submitProuctQuestions(
        @Query("product_id") productId: Int,
        @Query("question") questionAr: String
    ): OrderSuccess

    @GET("/api/v1/search")
    suspend fun filterProducts(
        @Query("category[]") categories: MutableList<Int>?,
        @Query("min_price") minPrice: Int?,
        @Query("max_price") maxPrice: Int?,
        @Query("page") pageNumber: Int
    ): CategoryProducts


    @GET("/api/v1/provider/orders")
    suspend fun getProviderOrders(
        @Query("status") statusId: Int,
        @Query("page") pageNumber: Int
    ): OrdersListResponse


    @GET("/api/v1/provider/orders/{orderId}")
    suspend fun getOrderDetails(@Path("orderId") orderId: Int): OrderDetailsVendorResponse

    @POST("/api/v1/provider/toggle-product-status/{productId}")
    suspend fun toggleProductStatus(@Path("productId") productId: Int): SuccessMessage



    @DELETE("/api/v1/provider/products/{product_id}")
    suspend fun deleteProduct(@Path("product_id") productId: Int): SuccessMessage


    @POST("/api/v1/provider/delete-all-products")
    suspend fun deleteAllProducts(): SuccessMessage

    @POST("/api/v1/provider/toggle-pinned-product/{productId}")
    suspend fun togglePinnedProduct(@Path("productId") productId: Int): SuccessMessage

    @PUT("/api/v1/provider/orders/{order_id}")
    @FormUrlEncoded
    suspend fun changeOrderStatus(
        @Path("order_id") order_id: Int,
        @Field("status") statusId: Int
    ): SuccessMessage


}