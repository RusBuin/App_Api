package com.example.kotlin

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://reqres.in/api/"

    private var retrofit: Retrofit? = null

    fun getApiService(): ApiService {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)  // Здесь задаем базовый URL
                .addConverterFactory(GsonConverterFactory.create())  // Для работы с JSON
                .build()
        }
        return retrofit!!.create(ApiService::class.java)
    }
}
