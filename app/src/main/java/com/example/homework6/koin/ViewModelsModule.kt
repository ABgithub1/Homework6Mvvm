package com.example.homework6.koin

import com.example.homework6.mvvm.DetailsViewModel
import com.example.homework6.mvvm.ListFromDatabaseViewModel
import com.example.homework6.mvvm.ListFromRetrofitViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listFromDatabaseViewModelModule = module {
    viewModel {
        ListFromDatabaseViewModel(get())
    }
}

val listFromRetrofitViewModelModule = module {
    viewModel {
        ListFromRetrofitViewModel(get())
    }
}

val detailsViewModelModule = module {
    viewModel {
        DetailsViewModel()
    }
}
