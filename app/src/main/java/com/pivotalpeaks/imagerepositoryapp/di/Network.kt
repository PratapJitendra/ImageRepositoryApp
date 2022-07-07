package com.pivotalpeaks.imagerepositoryapp.di

import android.content.Context
import coil.ImageLoader
import coil.util.CoilUtils
import com.pivotalpeaks.imagerepositoryapp.network.ImageService
import com.pivotalpeaks.imagerepositoryapp.network.RequestInterceptor
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Network {

    @Provides
    @Singleton
    fun okHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .cache(CoilUtils.createDefaultCache(context))
            .build()


    }

    @Provides
    @Singleton
    fun ImageLoader(@ApplicationContext context: Context,
    okHttpClient: OkHttpClient):ImageLoader{
        return ImageLoader.Builder(context)
            .okHttpClient{okHttpClient}
            .build()
    }

    @Provides
    @Singleton
    fun Retrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://gist.githubusercontent.com/PratapJitendra/1cece9b90b990cb654c69f4437485dcf/raw/6a6d7d8425efff4e81830fbc2136a20990a96969/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideImageService(retrofit: Retrofit):ImageService{
        return retrofit.create(ImageService::class.java)
    }
}