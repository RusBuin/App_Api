package com.example.kotlin

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface ApiService {
    @POST("register")
    fun registerUser(
        @Body registrationRequest: RegistrationRequest
    ): Call<RegistrationResponse>
}
