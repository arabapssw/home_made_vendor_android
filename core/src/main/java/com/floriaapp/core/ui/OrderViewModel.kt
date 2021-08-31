package com.floriaapp.core.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.floriaapp.core.Extensions.SingleLiveEvent
import com.floriaapp.core.Extensions.launchDataLoad
import com.floriaapp.core.Extensions.toErrorBody
import com.floriaapp.core.api.categoryApi
import com.floriaapp.core.data.data_source.category_paging.OrdersDataSource
import com.floriaapp.core.domain.model.Error.ErrorResponse
import com.floriaapp.core.domain.model.checkout.address.AddressBody
import com.floriaapp.core.domain.model.checkout.order.order_details.OrderDetailsResponse
import com.floriaapp.core.domain.model.checkout.shipping.AddressesResponse
import com.floriaapp.core.domain.model.checkout.shipping.ShippingCompaniesResponse
import com.floriaapp.core.domain.model.checkout.shipping.new.ShippinCostNeeded
import com.floriaapp.core.domain.model.orderSuccess.OrderSuccess
import com.floriaapp.core.domain.model.provider_.order_details.OrderDetailsVendorResponse
import com.floriaapp.core.domain.model.provider_.orders.OrderListResponseItem
import com.floriaapp.core.domain.model.provider_.orders.OrdersListResponse
import com.floriaapp.core.domain.model.success.SuccessMessage
import com.floriaapp.core.domain.model.summary.SummaryOrderResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class OrderViewModel(var categoryApi: categoryApi) : ViewModel() {

    var _PaymentOption = MutableLiveData<ShippingCompaniesResponse>()
    var PaymentOption: LiveData<ShippingCompaniesResponse> = _PaymentOption


    var _Addresses = MutableLiveData<AddressesResponse>()
    var Addresses: LiveData<AddressesResponse> = _Addresses

    var _OrderResponse = MutableLiveData<OrderDetailsResponse>()
    var OrderResponse: LiveData<OrderDetailsResponse> = _OrderResponse

    var _ShippingFees = MutableLiveData<ShippinCostNeeded>()
    var ShippingFees: LiveData<ShippinCostNeeded> = _ShippingFees




    var _OrdersList = MutableLiveData<OrderDetailsVendorResponse>()
    var OrdersList: LiveData<OrderDetailsVendorResponse> = _OrdersList



    var _ErrorLogin = MutableLiveData<ErrorResponse>()
    var ErrorLogin: LiveData<ErrorResponse> = _ErrorLogin

    var _SuccessOrderRate = MutableLiveData<SuccessMessage>()
    var SuccessOrderRate: LiveData<SuccessMessage> = _SuccessOrderRate



    var _SuccessOrderDone = MutableLiveData<SuccessMessage>()
    var SuccessOrderDone: LiveData<SuccessMessage> = _SuccessOrderDone


    var _SummaryOrder = MutableLiveData<SummaryOrderResponse>()
    var SummaryOrder: LiveData<SummaryOrderResponse> = _SummaryOrder


    var _Error = SingleLiveEvent<String>()
    var Error: SingleLiveEvent<String> = _Error

    fun getPaymentOption() {
//        launchDataLoad(execution = {
//            _PaymentOption.postValue(checkoutApi.getPaymentOptions())
//        }, errorReturned = {
//            Log.e("error", it.message.toString())
//            _Error.postValue(it.toErrorBody())
//        })
    }

    fun checkPromoCode(address: Int,shipping: String,promoCode:String?) {
//        launchDataLoad(execution = {
//            _SummaryOrder.postValue(checkoutApi.summaryOrder(address,shipping,promoCode))
//        }, errorReturned = {
//            if (it is HttpException) {
//                val errorBodyResponse = it.response()?.errorBody()?.string()
//                val gson = GsonBuilder().create()
//                val error = gson.fromJson(errorBodyResponse, ErrorResponse::class.java)
//                _ErrorLogin.postValue(error)
//            }
////            Log.e("error", it.message.toString())
////            _Error.postValue(it.toErrorBody())
//        })
    }



    fun getShippingFees( address: Int) {
//        launchDataLoad(execution = {
//            _ShippingFees.postValue(checkoutApi.getShippingFees(address))
//        }, errorReturned = {
//            Log.e("error", it.message.toString())
//            _Error.postValue(it.toErrorBody())
//        })
    }

    fun getShippingFeesExpress( address: Int,productID: Int,quantity: Int) {
//        launchDataLoad(execution = {
//            _ShippingFees.postValue(checkoutApi.getShippingFeesExpress(address,productID,quantity))
//        }, errorReturned = {
//            Log.e("error", it.message.toString())
//            _Error.postValue(it.toErrorBody())
//        })
    }


    fun submitOrder(
        shipping: String,
        address: Int,
        payment: Int,
        required: String?,
        notes: HashMap<String, String>,
        promoCode: String?,
        otherPayment:Int?,
    ) {
//        launchDataLoad(execution = {
//
//            _SuccessOrderDone.postValue(checkoutApi.submitOrder(shipping, address, payment, required,notes = notes,promoCode,otherPayment=otherPayment))
//        }, errorReturned = {
//            Log.e("error", it.message.toString())
//            _Error.postValue(it.toErrorBody())
//        })
    }

    fun checkoutExpress(
        shipping: String,
        address: Int,
        payment: Int,
        required: String?,
        notes: String?,
        promoCode: String?,
        productID:Int,
        quantity:Int,
        otherPayment:Int?,

        ) {
//        launchDataLoad(execution = {
//
//            _SuccessOrderDone.postValue(checkoutApi.checkoutExpress(shipping, address, payment, required,notes = notes,promoCode,productId = productID,qunatity=quantity,otherPayment=otherPayment))
//        }, errorReturned = {
//            Log.e("error", it.message.toString())
//            _Error.postValue(it.toErrorBody())
//        })
    }


    fun getShipping() {
//        launchDataLoad(execution = {
//            _PaymentOption.postValue(checkoutApi.getShippingCompanies())
//        }, errorReturned = {
//            Log.e("error", it.message.toString())
//            _Error.postValue(it.toErrorBody())
//        })
    }

    fun getAddress() {
      //  launchDataLoad(execution = {
//            _Addresses.postValue(checkoutApi.getAddresses())
//        }, errorReturned = {
//            Log.e("error", it.message.toString())
//            _Error.postValue(it.toErrorBody())
//        })
    }

    fun submitAddress(addressBody: AddressBody) {
//        launchDataLoad(execution = {
//            _SuccessOrderDone.postValue(checkoutApi.submitAddress(addressBody))
//        }, errorReturned = {
//            Log.e("error", it.message.toString())
//            _Error.postValue(it.toErrorBody())
//        })
    }
//
//    fun getOrderDetails(id:Int) {
////        launchDataLoad(execution = {
////            _OrderResponse.postValue(checkoutApi.getOrderDetails(id))
////        }, errorReturned = {
////            Log.e("error", it.message.toString())
////            _Error.postValue(it.toErrorBody())
////        })
//    }



    fun submitBankTransfer(orderId:Int,image: MultipartBody.Part?) {
//        launchDataLoad(execution = {
//            _SuccessOrder.postValue(checkoutApi.submitBankTransfer(orderId,image))
//        }, errorReturned = {
//            Log.e("error", it.message.toString())
//            _Error.postValue(it.toErrorBody())
//        })
    }


    fun rateOrder(orderId: Int,rate:Int,notes: String,shippinRate:Int,shippingNotes:String) {
//        launchDataLoad(execution = {
//            _SuccessOrderRate.postValue(checkoutApi.rateOrder(orderId,rate,notes,shippinRate,shippingNotes))
//        }, errorReturned = {
//            Log.e("error", it.message.toString())
//            _Error.postValue(it.toErrorBody())
//        })
    }


    fun confirmPayment(orderId:Int, tapId: String) {
//        launchDataLoad(execution = {
//            _SuccessOrder.postValue(checkoutApi.confirm_payment(orderId,tapId))
//        }, errorReturned = {
//            Log.e("error", it.message.toString())
//            _Error.postValue(it.toErrorBody())
//        })
    }



    fun changeOrderStatus(orderId:Int,orderStatus:Int) {
        launchDataLoad(execution = {
            _SuccessOrderDone.postValue(categoryApi.changeOrderStatus(orderId,orderStatus))
        }, errorReturned = {
            _Error.postValue(it.toErrorBody())
        })
    }


    fun getOrderDetails(orderId:Int) {
        launchDataLoad(execution = {
            _OrdersList.postValue(categoryApi.getOrderDetails(orderId))
        }, errorReturned = {
            _Error.postValue(it.toErrorBody())
        })
    }






    fun getOrdersLists(statusOfOrder: Int): Flow<PagingData<OrderListResponseItem>> {
        return Pager(PagingConfig(pageSize = 5)) {
            OrdersDataSource(categoryApi, statusOfOrder)
        }.flow.cachedIn(viewModelScope)
    }


}