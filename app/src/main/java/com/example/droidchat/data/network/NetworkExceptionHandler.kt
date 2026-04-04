package com.example.droidchat.data.network

import com.example.droidchat.model.NetworkException
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText

suspend fun <T> handleNetworkException(block: suspend () -> T):T {
    return try {
        block()
    } catch (e: ClientRequestException) {
        val errorManager = e.response.bodyAsText()
        throw NetworkException.ApiException(errorManager, e.response.status.value)
    } catch (e: Exception) {
        throw NetworkException.UnknownNetworkException(e)
    }
}