package com.cyn.wandroider.ui.http

import android.os.Build
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/7
 *    desc   :
 */
object NetworkService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com")
        .client(
            OkHttpClient.Builder().callTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val wanApi = retrofit.create(WanApi::class.java)
}