package com.sample.app.presentation.di

import com.sample.app.presentation.MainViewModel
import com.sample.app.presentation.router.NavigationRouter
import com.sample.app.presentation.ui.home.PlayersViewModel
import com.sample.app.presentation.ui.details.PlayerDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationKoinModule = module {
  single { NavigationRouter(get()) }
  viewModel {
    MainViewModel(get())
  }
  viewModel {
    PlayersViewModel(get(), get(), get())
  }
  viewModel {
    PlayerDetailsViewModel()
  }
}

