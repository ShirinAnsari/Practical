package com.shirinansaripractical.repository

import com.shirinansaripractical.webservice.APIClient
import com.shirinansaripractical.webservice.ApiService

object UserRepository {
    private val apiService: ApiService = APIClient.apiClient().create(ApiService::class.java)

    suspend fun getUsers() = apiService.getUsers()
}
