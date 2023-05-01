package com.example.baseproject.data.interceptor

import android.content.Context
import android.content.Intent
import com.example.baseproject.MainActivity
import com.example.baseproject.base.annotation.IgnoreBadRequestForGlobalError
import com.example.baseproject.base.annotation.IgnoreInternalErrorForGlobalError
import com.example.baseproject.base.extension.hasAnnotation
import com.example.baseproject.base.model.ErrorBody
import com.example.baseproject.base.model.NetworkException
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkExceptionInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val request = chain.request().newBuilder().build()
            val response = chain.proceed(request)
            if (!response.isSuccessful) {
                handResponseException(request, response)
            }
            return response
        } catch (e: Exception) {
            handleNetworkException(e)
            throw e
        }
    }

    private fun handleNetworkException(e: Exception) {
        if (e is UnknownHostException) {
            sendExceptionBroadcast(NetworkException.ConnectionError(e))
        }
    }

    private fun handResponseException(request: Request, response: Response) {
        if (!shouldIgnoreError(request, response)) {
            sendExceptionBroadcast(
                NetworkException.ResponseError(
                    response.code,
                    parseResponseErrorMessage(response)
                )
            )
        }
    }

    private fun shouldIgnoreError(request: Request, response: Response): Boolean {
        val isNotFound = response.code == HttpURLConnection.HTTP_NOT_FOUND
        val isBadRequest = response.code in arrayOf(
            HttpURLConnection.HTTP_BAD_REQUEST,
            HttpURLConnection.HTTP_FORBIDDEN
        ) &&
                request.hasAnnotation(IgnoreBadRequestForGlobalError::class.java)
        val isInternalErrorRequest = response.code == HttpURLConnection.HTTP_INTERNAL_ERROR &&
                request.hasAnnotation(IgnoreInternalErrorForGlobalError::class.java)
        return isNotFound || isBadRequest || isInternalErrorRequest
    }

    private fun parseResponseErrorMessage(response: Response): String? {
        return try {
            Gson().fromJson(response.body?.string(), ErrorBody::class.java).message
        } catch (e: Exception) {
            null
        }
    }

    private fun sendExceptionBroadcast(exception: NetworkException) {
        context.sendBroadcast(
            Intent(MainActivity.BROADCAST_NETWORK_EXCEPTION_ACTION)
                .putExtra(MainActivity.BROADCAST_NETWORK_EXCEPTION_EXTRA, exception)
        )
    }
}