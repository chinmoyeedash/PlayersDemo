package com.sample.app.domain.di

import com.sample.app.domain.usecase.base.GetPlayersFlowUseCase
import com.sample.app.domain.usecase.base.GetPlayersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val domainKoinModule = module {
  single { GetPlayersUseCase(get()) }
  single { GetPlayersFlowUseCase(get()) }

}

