package com.test.utils.Common.di

import com.floriaapp.core.api.*
import com.test.utils.Common.network.createNetworkClient
import org.koin.dsl.module
import retrofit2.Retrofit


val retrofit: Retrofit = createNetworkClient()
private val generalapi: generalApi = retrofit.create(generalApi::class.java)
private val loginapi: loginApi = retrofit.create(loginApi::class.java)
private val CAATEGORY_API: productsApi = retrofit.create(productsApi::class.java)
private val cartApii: cartApi = retrofit.create(cartApi::class.java)
private val checkout: checkout = retrofit.create(com.floriaapp.core.api.checkout::class.java)


val networkModule = module {
    factory { generalapi }
    factory { loginapi }
    factory { CAATEGORY_API }
    factory { cartApii }
    factory { checkout }

}
