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
import com.floriaapp.core.domain.model.general.GeneralDataResponse
import com.floriaapp.core.domain.model.orderSuccess.OrderSuccess
import com.floriaapp.core.domain.model.product.ProductDetailsResponse
import com.floriaapp.core.domain.model.provider_.productsVendor.ProviderProductsResponseItem
import com.floriaapp.core.domain.model.success.SuccessMessage
import com.floriaapp.core.domain.model.tagItems.TagsProducts
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProductsViewModel(var productsApi: productsApi) : ViewModel() {

    var _CategoriesData = MutableLiveData<CategoriesResponse>()
    var CategoriesData: LiveData<CategoriesResponse> = _CategoriesData

    var _ProductDetails = MutableLiveData<ProductDetailsResponse>()
    var ProductDetails: LiveData<ProductDetailsResponse> = _ProductDetails

    private var _TagProducts = MutableLiveData<TagsProducts>()
    var tagsProducts: LiveData<TagsProducts> = _TagProducts

    var _SuccessMessage = MutableLiveData<SuccessMessage>()
    var SuccessMessage: LiveData<SuccessMessage> = _SuccessMessage


    var _GeneralData = MutableLiveData<GeneralDataResponse>()
    var GeneralData: LiveData<GeneralDataResponse> = _GeneralData

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

    fun getGeneralData() {

        launchDataLoad(execution = {
            _GeneralData.postValue(productsApi.getGeneralData())
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


    fun deleteProductsORProducts(productId: Int? = null, typeOfFav: Int) {
        launchDataLoad(execution = {

            _SuccessMessage.value = when (typeOfFav) {
                1 -> productsApi.deleteProduct(productId!!)
                2 -> productsApi.deleteAllProducts()
                else -> null
            }
        }, errorReturned = {
            Log.e("error", it.message.toString())
            _Error.postValue(it.toErrorBody())
        })

    }

    fun storeProduct(productBody: objectData) {
        launchDataLoad(execution = {

            _SuccessMessage.value = productsApi.storeProduct(
                nameArabic = productBody.nameArabic,
                nameEnglish = productBody.nameEnglish,
                descriptionArabic = productBody.descriptionArabic,
                descriptionEnglish = productBody.descriptionEnglish,
                discountStart = productBody.discount_start_date,
                discountEnd = productBody.discount_end_date,
                sku = productBody.sku,
                priceProduct = productBody.price,
                discount = productBody.discount,
                weight = productBody.weight,
                quantity = productBody.quantity,
                image = productBody.image,
                active = productBody.active,
                pinned = productBody.pinned,
                categories = productBody.categories,
                tags = productBody.tags,
                images = productBody.images
            )

        }, errorReturned = {
            Log.e("error", it.message.toString())
            _Error.postValue(it.toErrorBody())
        })
    }

    fun editProduct(productBody: objectData, productId: Int) {
        launchDataLoad(execution = {

            _SuccessMessage.value = productsApi.editProduct(
                nameArabic = productBody.nameArabic,
                nameEnglish = productBody.nameEnglish,
                descriptionArabic = productBody.descriptionArabic,
                descriptionEnglish = productBody.descriptionEnglish,
                discountStart = productBody.discount_start_date,
                discountEnd = productBody.discount_end_date,
                sku = productBody.sku,
                priceProduct = productBody.price,
                discount = productBody.discount,
                weight = productBody.weight,
                quantity = productBody.quantity,
                image = productBody.image,
                active = productBody.active,
                pinned = productBody.pinned,
                categories = productBody.categories,
                tags = productBody.tags,
                images = productBody.images,
                productId = productId,
                method = RequestBody.create(MediaType.get("text/plain"), "PUT")
            )

        }, errorReturned = {
            Log.e("error", it.message.toString())
            _Error.postValue(it.toErrorBody())
        })
    }


}

object objectData {
    var nameArabic: RequestBody? = null
    var nameEnglish: RequestBody? = null
    var descriptionArabic: RequestBody? = null
    var descriptionEnglish: RequestBody? = null
    var sku: RequestBody? = null
    var price: RequestBody? = null
    var discount: RequestBody? = null
    var discount_start_date: RequestBody? = null
    var discount_end_date: RequestBody? = null
    var weight: RequestBody? = null
    var quantity: RequestBody? = null
    var image: MultipartBody.Part? = null
    var active: RequestBody? = null
    var pinned: RequestBody? = null
    var categories: MutableList<Int>? = null
    var tags: MutableList<Int>? = null
    var images: ArrayList<MultipartBody.Part>? = null

}



