package com.example.baseproject.data

import com.example.baseproject.data.model.User
import retrofit2.http.GET

interface MainService {
    @GET("users")
    suspend fun getListUser(): List<User>
}