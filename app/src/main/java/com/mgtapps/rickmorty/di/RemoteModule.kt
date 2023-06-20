package com.mgtapps.rickmorty.di

import com.mgtapps.rickmorty.common.Constants.BASE_URL
import com.mgtapps.rickmorty.data.remote.RickMortyApi
import com.mgtapps.rickmorty.data.repository.RepositoryImpl
import com.mgtapps.rickmorty.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun ProvideRickMortyApi(): RickMortyApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RickMortyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(api : RickMortyApi) : CharacterRepository {
        return RepositoryImpl(api)
    }

}