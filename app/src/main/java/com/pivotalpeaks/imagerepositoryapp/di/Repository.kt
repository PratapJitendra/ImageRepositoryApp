package com.pivotalpeaks.imagerepositoryapp.di

import com.pivotalpeaks.imagerepositoryapp.network.ImageService
import com.pivotalpeaks.imagerepositoryapp.persistence.ImageDao
import com.pivotalpeaks.imagerepositoryapp.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object Repository {

    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        imageService: ImageService,
        imageDao: ImageDao
    ):MainRepository{
        return MainRepository(imageService,imageDao)
    }
}