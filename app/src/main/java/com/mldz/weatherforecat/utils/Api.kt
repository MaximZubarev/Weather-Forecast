package com.mldz.weatherforecat.utils

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
    private const val TOKEN: String = "b6907d289e10d714a6e88b30761fae22"

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