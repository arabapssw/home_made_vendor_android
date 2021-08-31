package com.test.utils.Common.di

import com.floriaapp.core.ui.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ProductsViewModel(get()) }
    viewModel { CartViewModel(get()) }
    viewModel { OrderViewModel(get()) }

}