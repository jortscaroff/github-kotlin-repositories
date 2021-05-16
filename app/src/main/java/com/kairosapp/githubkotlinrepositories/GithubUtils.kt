package com.kairosapp.githubkotlinrepositories

import android.net.Uri
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@ExperimentalSerializationApi
class GithubUtils {

    private companion object {
        const val clientId = BuildConfig.CONSUMER_KEY
        const val clientSecret = BuildConfig.CONSUMER_SECRET
        const val scheme ="https"
        const val host ="github.com"
        const val redirectUrl = "jortscaroff://callback"
        const val scopes = ""
    }

    private val converterFactory: Converter.Factory by lazy {
        Json { ignoreUnknownKeys = true }
            .asConverterFactory("application/json".toMediaType())
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .baseUrl(HttpUrl.Builder().scheme(scheme).host(host).build())
            .addConverterFactory(converterFactory)
            .build()
    }

    fun buildAuthGitHubUrl(): Uri {
        return Uri.Builder()
            .scheme(scheme)
            .authority(host)
            .appendEncodedPath("login/oauth/authorize")
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("scope", scopes)
            .appendQueryParameter("redirect_url", redirectUrl)
            .build()
    }

    fun getCodeFromUri(uri: Uri?): String? {
        uri ?: return null
        if (!uri.toString().startsWith(redirectUrl)) {
            return null
        }
        return uri.getQueryParameter("code")
    }
}