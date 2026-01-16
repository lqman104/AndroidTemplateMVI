package com.luqman.android.template.core.network.client

import com.luqman.android.template.core.network.error.HttpError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.util.reflect.TypeInfo
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class AppHttpClientImpl @Inject constructor(
    private val client: HttpClient
) : AppHttpClient {

    override suspend fun <T : Any> get(
        url: String,
        typeInfo: TypeInfo,
        block: HttpRequestBuilder.() -> Unit
    ): T = handleExceptions {
        client.get(url) { block() }
            .body(typeInfo)
    }

    override suspend fun <T> post(
        url: String,
        typeInfo: TypeInfo,
        body: Any?,
        block: HttpRequestBuilder.() -> Unit
    ): T = handleExceptions {
        client.post(url) {
            setBody(body)
            block()
        }.body(typeInfo)
    }

    override suspend fun <T> put(
        url: String,
        typeInfo: TypeInfo,
        body: Any?,
        block: HttpRequestBuilder.() -> Unit
    ): T = handleExceptions {
        client.put(url) {
            setBody(body)
            block()
        }.body(typeInfo)
    }

    override suspend fun <T> delete(
        url: String,
        typeInfo: TypeInfo,
        block: HttpRequestBuilder.() -> Unit
    ): T = handleExceptions {
        client.delete(url) { block() }.body(typeInfo)
    }

    private suspend fun <T> handleExceptions(
        block: suspend () -> T
    ): T {
        return try {
            block()
        } catch (e: ClientRequestException) {
            // 4xx
            val status = e.response.status
            val message = safeErrorMessage(e)

            when (status) {
                HttpStatusCode.BadRequest -> throw HttpError.BadRequest(message)
                HttpStatusCode.Unauthorized -> throw HttpError.Unauthorized(message)
                HttpStatusCode.NotFound -> throw HttpError.NotFound(message)
                else -> throw HttpError.Unknown(message, status.value)
            }
        } catch (e: ServerResponseException) {
            // 5xx
            val message = safeErrorMessage(e)
            throw HttpError.ServerError(message)
        } catch (_: HttpRequestTimeoutException) {
            throw HttpError.Timeout("Request timeout")
        } catch (_: UnknownHostException) {
            throw HttpError.NoInternet("No internet connection")
        } catch (_: IOException) {
            throw HttpError.NoInternet("Network error")
        } catch (e: Exception) {
            if (e is HttpError) throw e
            throw HttpError.Unknown(e.message ?: "Unknown error")
        }
    }

    private suspend fun safeErrorMessage(e: Exception): String {
        return try {
            when (e) {
                is ClientRequestException -> e.response.body<String>()
                is ServerResponseException -> e.response.body<String>()
                else -> e.message ?: "Unknown error"
            }
        } catch (_: Exception) {
            e.message ?: "Unknown error"
        }
    }
}
