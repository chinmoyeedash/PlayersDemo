package com.sample.app

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.app.sample.ActivityLifecycleCallbacklImpl
import com.sample.app.data.di.dataKoinModule
import com.sample.app.domain.di.domainKoinModule
import com.sample.app.presentation.di.presentationKoinModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SampleApplication: Application() {

  override fun onCreate() {
    super.onCreate()
    initKoinModules()
  }


  @OptIn(ExperimentalCoroutinesApi::class)
  private fun initKoinModules() {
    startKoin {
      androidLogger()
      androidContext(this@SampleApplication)
      modules(
        presentationKoinModule + dataKoinModule + domainKoinModule +
                module { single { this@SampleApplication } }
      )
    }
  }
}