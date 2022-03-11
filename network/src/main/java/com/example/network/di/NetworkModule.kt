package com.example.network.di

import com.example.domain.common.Constants
import com.example.domain.repository.FileRepository
import com.example.network.remote.FileApi
import com.example.network.repository.FileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideFileApi(): FileApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFileRepository(api: FileApi): FileRepository = FileRepositoryImpl(api)
}
