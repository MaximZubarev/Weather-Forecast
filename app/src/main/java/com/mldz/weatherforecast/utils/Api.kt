package com.mldz.weatherforecast.utils

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.HttpUrl
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


/**
Created by Maxim Zubarev on 2019-08-23.
 */
object Api {
    private const val BASE_URL: String = "https://api.openweathermap.org/"
    private const val TOKEN: String = "cc33d8f881b375af6cca61900288924a"

    fun create(): ApiEndpoint {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient().newBuilder().addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val url: HttpUrl = chain.request().url
                    .newBuilder()
                    .addQueryParameter("units", "metric")
                    .addQueryParameter("appid", TOKEN)
                    .build()
                val request = chain.request().newBuilder().url(url).build()
                return chain.proceed(request)
            }
        })
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()


        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(ApiEndpoint::class.java)
    }
}