package com.floriaapp.core.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.floriaapp.core.Extensions.launchDataLoad
import com.floriaapp.core.Extensions.toErrorBody
import com.floriaapp.core.api.productsApi
import com.floriaapp.core.data.data_source.category_paging.ProductsDataSource
import com.floriaapp.core.data.data_source.category_paging.TagProductDataSource
import com.floriaapp.core.domain.model.category.CategoriesResponse
import com.floriaapp.core.domain.model.category.categoryProductItem
import com.floriaapp.core.domain.model.orderSuccess.Data
import com.floriaapp.core.domain.model.orderSuccess.Meta
import com.floriaapp.core.domain.model.orderSuccess.OrderSuccess
import com.floriaapp.core.domain.model.product.ProductDetailsResponse
import com.floriaapp.core.domain.model.provider_.productsVendor.ProviderProductsResponseItem
import com.floriaapp.core.domain.model.success.SuccessMessage
import com.floriaapp.core.domain.model.tagItems.TagsProducts
import kotlinx.coroutines.flow.Flow

class ProductsViewModel(var productsApi: productsApi) : ViewModel() {

    var _CategoriesData = MutableLiveData<CategoriesResponse>()
    var CategoriesData: LiveData<CategoriesResponse> = _CategoriesData

    var _ProductDetails = MutableLiveData<ProductDetailsResponse>()
    var ProductDetails: LiveData<ProductDetailsResponse> = _ProductDetails

    private var _TagProducts = MutableLiveData<TagsProducts>()
    var tagsProducts: LiveData<TagsProducts> = _TagProducts

    var _SuccessMessage = MutableLiveData<SuccessMessage>()
    var SuccessMessage: LiveData<SuccessMessage> = _SuccessMessage


    var _SendQuestionResponse = MutableLiveData<OrderSuccess>()
    var SendQuestionResponse: LiveData<OrderSuccess> = _SendQuestionResponse


    var _Error = MutableLiveData<String>()
    var Error: LiveData<String> = _Error

    fun getCategories() {
        launchDataLoad(execution = {
            _CategoriesData.postValue(productsApi.getCategories())
        }, errorReturned = {
            Log.e("error", it.message.toString())
            //_Error.postValue(it.toErrorBody())
        })
    }

    fun getProductDetails(productId: Int) {
        launchDataLoad(execution = {
            _ProductDetails.postValue(productsApi.getProductDetails(productId))
        }, errorReturned = {
            Log.e("error", it.message.toString())
            //_Error.postValue(it.toErrorBody())
        })
    }


    fun submitQuestions(productId: Int, questionAr: String) {
        launchDataLoad(execution = {
            _SendQuestionResponse.postValue(
                productsApi.submitProuctQuestions(
                    productId = productId,
                    questionAr = questionAr
                )
            )
        }, errorReturned = {
            Log.e("error", it.message.toString())
            //_Error.postValue(it.toErrorBody())
        })
    }


    fun getCategoryProducts(categoryId: Int): Flow<PagingData<ProviderProductsResponseItem>> {
        return Pager(PagingConfig(pageSize = 5)) {
            ProductsDataSource(categoryId, productsApi, 1)
        }.flow.cachedIn(viewModelScope)
    }


    fun getAllProducts(): Flow<PagingData<ProviderProductsResponseItem>> {
        return Pager(PagingConfig(pageSize = 5)) {
            ProductsDataSource(productsApi = productsApi, typeOfApi = 2)
        }.flow.cachedIn(viewModelScope)
    }

    fun getFeaturedProducts(): Flow<PagingData<ProviderProductsResponseItem>> {
        return Pager(PagingConfig(pageSize = 5)) {
            ProductsDataSource(productsApi = productsApi, typeOfApi = 3)
        }.flow.cachedIn(viewModelScope)
    }


    fun getTagProducts(tagId: Int): Flow<PagingData<categoryProductItem>> {
        return Pager(PagingConfig(pageSize = 2)) {
            TagProductDataSource(productsApi = productsApi, tagId = tagId)
        }.flow.cachedIn(viewModelScope)
    }

    fun getTagProductsNormal(tagId: Int) {
        launchDataLoad(execution = {
            _TagProducts.postValue(productsApi.getTagProducts(tagId))
        }, errorReturned = {
            Log.e("error", it.message.toString())
            _Error.postValue(it.toErrorBody())
        })
    }


    fun toggleProductPinnedOrStatus(productId: Int, typeOfFav: Int) {
        launchDataLoad(execution = {

            _SuccessMessage.value = when (typeOfFav) {
                1 -> productsApi.toggleProductStatus(productId)
                2 -> productsApi.togglePinnedProduct(productId)
                else -> null
            }
        }, errorReturned = {
            Log.e("error", it.message.toString())
            //_Error.postValue(it.toErrorBody())
        })

    }


}