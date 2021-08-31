package com.floriaapp.core.Extensions

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.floriaapp.core.domain.model.Error.ErrorResponse
import com.floriaapp.core.domain.model.Error.Errors
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*


fun ViewModel.launchDataLoad(
    execution: suspend CoroutineScope.() -> Unit,
    errorReturned: suspend CoroutineScope.(Throwable) -> Unit,
    finallyBlock: (suspend CoroutineScope.() -> Unit)? = null
) {

    this.viewModelScope.launch {
        try {
            execution()
        } catch (e: Throwable) {
            errorReturned(e)
        } finally {
            finallyBlock?.invoke(this)
        }
    }
}

fun Context?.setLocale(langugeNeeded: String) {
    val locale = Locale(langugeNeeded)
    Locale.setDefault(locale)
    val config = Configuration()
    config.locale = locale
    this?.resources?.updateConfiguration(
        config,
        this.resources.displayMetrics
    )
}

fun Throwable?.toErrorBody(): String? {
    return when (this) {
        is SocketTimeoutException -> " Check Your Network Connection , Try Again later "
        is ConnectException -> " Check Your Network Connection , Try Again later "
        is UnknownHostException -> message.toString() + " Try again later "
        is HttpException -> {
            //message.toString()
            val errorBodyResponse = response()?.errorBody()?.string()
            val gson = GsonBuilder().create()
            val error = gson.fromJson(errorBodyResponse, ErrorResponse::class.java)
            return when (true) {
             //   error.errors?.message2?.isNotEmpty() -> error.errors.message2
                error.errors?.phone?.isNotEmpty() -> error.errors.phone[0]
                error.errors?.email?.isNotEmpty() -> error.errors.email[0]
                error.errors?.password?.isNotEmpty() -> error.errors.password[0]
                error.errors?.code?.isNotEmpty() -> error.errors.code[0]
                error.errors?.avatar?.isNotEmpty() -> error.errors.avatar[0]
                error.errors?.dial_code?.isNotEmpty() -> error.errors.dial_code[0]
                error.errors?.message?.isNotEmpty() -> error.errors.message[0]
                error.errors?.apartment_number?.isNotEmpty() -> error.errors.apartment_number[0]
                error.errors?.another_phone?.isNotEmpty() -> error.errors.another_phone[0]
                error.errors?.newPasswordError?.isNotEmpty() -> error.errors.newPasswordError[0]
                error.errors?.promoCode?.isNotEmpty() -> error.errors.promoCode[0]
                error.errors?.notes?.isNotEmpty() -> error.errors.notes[0]
                error.errors?.shipping_notes?.isNotEmpty() -> error.errors.shipping_notes[0]


                else -> error.message
            }

        }
        else -> this?.message.toString()
    }
}