@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.rahullohra.instagram.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

expect object SslSettings {
    fun configureHttpClientConfig(config: HttpClientConfig<*>)
    fun createHttpClient(): HttpClient
}
