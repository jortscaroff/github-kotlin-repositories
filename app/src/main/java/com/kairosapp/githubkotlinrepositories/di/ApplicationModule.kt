package com.kairosapp.githubkotlinrepositories.di

import com.kairosapp.githubkotlinrepositories.api.GithubService
import com.kairosapp.githubkotlinrepositories.api.RepositoryRetriever
import com.kairosapp.githubkotlinrepositories.api.RepositoryRetrieverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideGithubRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGithubService(githubRetrofit : Retrofit): GithubService =
        githubRetrofit.create(GithubService::class.java)

    @Provides
    @Singleton
    fun provideRepositoryRetriever(githubService: GithubService) : RepositoryRetriever =
        RepositoryRetrieverImpl(githubService)
}