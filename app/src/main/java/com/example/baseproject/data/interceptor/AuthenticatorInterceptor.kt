package com.example.baseproject.data.interceptor

import com.example.baseproject.di.PreferenceProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticatorInterceptor @Inject constructor(
    private val preferenceProvider: PreferenceProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val userToken = preferenceProvider.userToken

        if (userToken.isNotBlank()) {
            requestBuilder.addHeader("Authorization", "Bearer $userToken")
        }
        return chain.proceed(requestBuilder.build())
    }
}