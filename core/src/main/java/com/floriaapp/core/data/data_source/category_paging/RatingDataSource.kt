package com.floriaapp.core.data.data_source.category_paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.floriaapp.core.api.productsApi
import com.floriaapp.core.domain.model.rating.RatingData
import com.floriaapp.core.domain.model.rating.RatingDataItem


class RatingDataSource(
    val productsApi: productsApi
) : PagingSource<Int, RatingDataItem>() {

    lateinit var categoryProducts: RatingData
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RatingDataItem> {

        try {
            val currentLoadingPageKey = params.key ?: 1
            categoryProducts = productsApi.getRatings(currentLoadingPageKey)
            val response = categoryProducts
            val responseData = mutableListOf<RatingDataItem>()
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

    override fun getRefreshKey(state: PagingState<Int, RatingDataItem>): Int? {
        return null
    }

}