@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.rahullohra.instagram.network

import com.rahullohra.instagram.IgAndroidApp
import com.rahullohra.instagram.R
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

actual object SslSettings {

    fun getKeyStore(): KeyStore {
//        val keyStoreFile = IgAndroidApp.INSTANCE.resources.openRawResource(R.raw.ssl_keystore)
////        val keyStoreFile = FileInputStream("ssl_keystore.jks")
//        val keyStorePassword = "foobar".toCharArray()
        val keyStore: KeyStore = KeyStore.getInstance("PKCS12")
        keyStore.load(IgAndroidApp.INSTANCE.resources.openRawResource(R.raw.ssl_keystore),"foobar".toCharArray())
        return keyStore
    }

    /**
     * keytool -importkeystore \
     *     -srckeystore keystore.jks \
     *     -destkeystore keystore.p12 \
     *     -srcstoretype JKS \
     *     -deststoretype PKCS12 \
     *     -srcstorepass yourpassword \
     *     -deststorepass yourpassword
     *
     */
    fun getTrustManagerFactory(): TrustManagerFactory {
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(getKeyStore())
        return trustManagerFactory
    }

    fun getSslContext(): SSLContext {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, getTrustManagerFactory().trustManagers, null)
        return sslContext
    }

    fun getTrustManager(): X509TrustManager {
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)
        return trustManagerFactory.trustManagers.first { it is X509TrustManager } as X509TrustManager
    }

    actual fun configureHttpClientConfig(config: HttpClientConfig<*>){
        config.engine {
            (this as OkHttpConfig).config {

                sslSocketFactory(getSslContext().socketFactory, getTrustManager())
                hostnameVerifier { _, _ -> true } // Accepts all hostnames
            }
        }
    }

    actual fun createHttpClient(): HttpClient {

        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json { isLenient = true; ignoreUnknownKeys = true })
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            engine {
                config {
                    readTimeout(60, TimeUnit.SECONDS)
                    followRedirects(true)
                }
            }
            configureHttpClientConfig(this)
        }
    }
}