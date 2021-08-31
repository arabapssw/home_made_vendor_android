package com.floriaapp.core.data.data_source.category_paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.floriaapp.core.api.productsApi
import com.floriaapp.core.domain.model.provider_.orders.OrderListResponseItem
import com.floriaapp.core.domain.model.provider_.orders.OrdersListResponse

class OrdersDataSource(val checkout: productsApi, val statusOfOrder:Int) : PagingSource<Int, OrderListResponseItem>() {

    lateinit var orderProducts: OrdersListResponse

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OrderListResponseItem> {

        try {
            val currentLoadingPageKey = params.key ?: 1
            orderProducts = checkout.getProviderOrders(statusOfOrder,currentLoadingPageKey)
            val response = orderProducts
            val responseData = mutableListOf<OrderListResponseItem>()
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

    override fun getRefreshKey(state: PagingState<Int, OrderListResponseItem>): Int? {
        return null
    }

}