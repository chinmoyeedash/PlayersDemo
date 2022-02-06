package com.sample.app.domain.di

import com.sample.app.domain.usecase.GetMemesFlowUseCase
import com.sample.app.domain.usecase.GetMemesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val domainKoinModule = module {
  single { GetMemesUseCase(get()) }
  single { GetMemesFlowUseCase(get()) }

}

