package com.example.kotlin

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    fun registerUser(
        @Body registrationRequest: RegistrationRequest
    ): Call<RegistrationResponse>

    @GET("users")
    fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 5
    ): Call<UserResponse>
}
