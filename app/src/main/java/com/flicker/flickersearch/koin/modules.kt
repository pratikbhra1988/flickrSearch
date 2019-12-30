package com.flicker.flickersearch.koin

import com.flicker.flickersearch.api.BackendAPI
import com.flicker.flickersearch.api.repository.HomeRepository
import com.flicker.flickersearch.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { HomeViewModel(get()) }
    single { HomeRepository() }
    single { BackendAPI() }
}
