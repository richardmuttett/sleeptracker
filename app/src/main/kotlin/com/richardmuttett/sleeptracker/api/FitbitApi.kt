package com.richardmuttett.sleeptracker.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FitbitApi {
  companion object {
    private const val BASE_URL = "https://api.fitbit.com"
    private const val ACCESS_TOKEN = "-add-access-token-here"
  }

  private val service: FitbitService

  init {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val client = OkHttpClient().newBuilder()
        .addInterceptor(interceptor)
        .addInterceptor { chain ->
          val request = chain.request()
              .newBuilder()
              .addHeader("Authorization", String.format("Bearer %s", ACCESS_TOKEN))
              .addHeader("Accept", "application/json")
              .build()
          chain.proceed(request)
        }.build()

    val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    service = retrofit.create(FitbitService::class.java)
  }

  fun getSleep(afterDate: String, limit: Int) = service.getSleep(afterDate, limit)
}
