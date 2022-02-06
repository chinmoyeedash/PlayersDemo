package com.sample.app

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.app.sample.ActivityLifecycleCallbacklImpl
import com.sample.app.data.di.dataKoinModule
import com.sample.app.domain.di.domainKoinModule
import com.sample.app.presentation.di.presentationKoinModule
import com.sample.app.presentation.router.IAppLifecycle
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SampleApplication: Application(), IAppLifecycle {
  override var foregroundActivity: AppCompatActivity? = null

  override fun onCreate() {
    super.onCreate()
    initAppLifecycle()
    initKoinModules()

  }
  private fun initAppLifecycle() {
    registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacklImpl() {
      override fun onActivityResumed(activity: Activity) {
        activity.let {
          if (it is AppCompatActivity) {
            foregroundActivity = it
          } else {
            throw RuntimeException("$it must inherit AppCompatActivity")
          }
        }
      }
    })
  }

  private fun initKoinModules() {
    startKoin {
      androidLogger()
      androidContext(this@SampleApplication)
      modules(
        presentationKoinModule + dataKoinModule + domainKoinModule +
                module { single<IAppLifecycle> { this@SampleApplication } }
      )
    }
  }
}