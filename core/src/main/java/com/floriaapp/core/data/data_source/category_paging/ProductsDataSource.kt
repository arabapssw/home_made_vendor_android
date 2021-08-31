package com.floriaapp.core.data.data_source.category_paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.floriaapp.core.api.productsApi
import com.floriaapp.core.domain.model.FilterData
import com.floriaapp.core.domain.model.provider_.productsVendor.ProviderProductsResponse
import com.floriaapp.core.domain.model.provider_.productsVendor.ProviderProductsResponseItem

class ProductsDataSource(
    val categoryId: Int? = null,
    val productsApi: productsApi,
    val typeOfApi: Int,
    var searchWord: String? = null,
    var filterData: FilterData? = null
) : PagingSource<Int, ProviderProductsResponseItem>() {

    lateinit var providerProducts: ProviderProductsResponse
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProviderProductsResponseItem> {

        try {
            val currentLoadingPageKey = params.key ?: 1
            providerProducts = productsApi.getAllProducts(currentLoadingPageKey)
//            providerProducts = when (typeOfApi) {
//                1 -> categoryApi.getCategoryProducts(categoryId, currentLoadingPageKey)
//                2 -> categoryApi.getAllProducts(currentLoadingPageKey)
//                3 -> categoryApi.getFeautredProducts(currentLoadingPageKey)
//                4 -> categoryApi.getFavourites(currentLoadingPageKey)
//                5 -> categoryApi.search(searchWord!!, currentLoadingPageKey)
//                6 -> categoryApi.filterProducts(
//                    categories = filterData?.categoriesList,
//                    minPrice = filterData?.min_price,
//                    maxPrice = filterData?.max_price,
//                    currentLoadingPageKey
//                )
             //   else -> providerProducts

            val response = providerProducts
            val responseData = mutableListOf<ProviderProductsResponseItem>()
            val data = response.data
            if (data != null) {
                responseData.addAll(data)
            }

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = if (data?.isEmpty() == true) null else currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProviderProductsResponseItem>): Int? {
        return null
    }

}