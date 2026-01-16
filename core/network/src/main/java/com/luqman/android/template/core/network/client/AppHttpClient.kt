package com.luqman.android.template.core.network.client

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.util.reflect.TypeInfo

interface AppHttpClient {
    suspend fun <T : Any> get(
        url: String,
        typeInfo: TypeInfo,
        block: HttpRequestBuilder.() -> Unit = {}
    ): T

    suspend fun <T> post(
        url: String,
        typeInfo: TypeInfo,
        body: Any? = null,
        block: HttpRequestBuilder.() -> Unit = {}
    ): T

    suspend fun <T> put(
        url: String,
        typeInfo: TypeInfo,
        body: Any? = null,
        block: HttpRequestBuilder.() -> Unit = {}
    ): T

    suspend fun <T> delete(
        url: String,
        typeInfo: TypeInfo,
        block: HttpRequestBuilder.() -> Unit = {}
    ): T
}
