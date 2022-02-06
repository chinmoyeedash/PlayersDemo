package com.sample.app.presentation.di

import com.sample.app.presentation.ui.home.MemesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
val presentationKoinModule = module {


  viewModel {
    MemesViewModel(get(), get())
  }

}

