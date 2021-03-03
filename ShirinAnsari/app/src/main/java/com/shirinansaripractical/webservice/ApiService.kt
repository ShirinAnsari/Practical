package com.shirinansaripractical.webservice

import com.shirinansaripractical.model.UserResponse
import retrofit2.http.GET

interface ApiService {

    @GET("?results=100")
    suspend fun getUsers(): UserResponse
}