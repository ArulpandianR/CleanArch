package com.t2s.task.network

import com.t2s.task.BuildConfig

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkModule {

    fun provideOkHttpClient(store: String): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader("Content-Type", "application/json")
            requestBuilder.addHeader("Accept", "application/json")
            requestBuilder.addHeader("store", store)
            requestBuilder.addHeader(original.method(), original.body().toString())
            chain.proceed(requestBuilder.build())
        }
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }
        httpClient.connectTimeout(120, TimeUnit.SECONDS)
        httpClient.readTimeout(120, TimeUnit.SECONDS)
        httpClient.writeTimeout(60, TimeUnit.SECONDS)
        /*  val cacheSize = 10 * 1024 * 1024 // 10 MiB
          val cache = Cache(context.cacheDir, cacheSize.toLong())
          httpClient.cache(cache)*/
        httpClient.retryOnConnectionFailure(true)
        return httpClient.build()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()


}