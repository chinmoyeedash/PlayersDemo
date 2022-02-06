package com.sample.app.data.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.sample.app.BuildConfig
import com.sample.app.data.api.ApiService
import com.sample.app.data.repository.PlayerRepository
import com.sample.app.domain.repository.IPlayersRepository
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import org.koin.android.ext.koin.androidContext
import java.io.IOException

private const val TIME_OUT = 30L

val dataKoinModule = module {

  single { createService(get()) }
  single { createPlayerRepository(get()) }
  single { createRetrofit(get(), BuildConfig.BASE_URL) }
  single { createOkHttpClient(androidContext()) }
}


fun createOkHttpClient(context: Context): OkHttpClient {

  val cacheSize = (10 *  1024 * 1024).toLong() // 10 MB
  val cache = Cache(context.cacheDir, cacheSize)
  val httpLoggingInterceptor = HttpLoggingInterceptor()
  httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
  return OkHttpClient.Builder()
    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
    .addInterceptor(httpLoggingInterceptor)
    .addInterceptor(offlineInterceptor(context))
    .addNetworkInterceptor(onlineInterceptor)
    .cache(cache)
    .build()
}

class offlineInterceptor(
  private val context: Context
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    var request: Request = chain.request()
      if (!isInternetAvailable(context)) {
        val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
        request = request.newBuilder()
          .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
          .removeHeader("Pragma")
          .build()
      }
    return chain.proceed(request)
  }

}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
  return Retrofit.Builder()
    .baseUrl(url)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create()).build()
}

fun createService(retrofit: Retrofit): ApiService {
  return retrofit.create(ApiService::class.java)
}

fun createPlayerRepository(apiService: ApiService): IPlayersRepository {
  return PlayerRepository(apiService)
}

var onlineInterceptor: Interceptor = object : Interceptor {
  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val response: Response = chain.proceed(chain.request())
    val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
    return response.newBuilder()
      .header("Cache-Control", "public, max-age=$maxAge")
      .removeHeader("Pragma")
      .build()
  }
}

fun isInternetAvailable(context: Context): Boolean {
  val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  val networkCapabilities = connectivityManager.activeNetwork ?: return false
  val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
  return when {
    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
    else -> false
  }
}
