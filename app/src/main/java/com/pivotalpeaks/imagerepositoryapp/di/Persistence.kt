package com.pivotalpeaks.imagerepositoryapp.di

import android.app.Application
import androidx.room.Room
import com.pivotalpeaks.imagerepositoryapp.R
import com.pivotalpeaks.imagerepositoryapp.persistence.AppDatabase
import com.pivotalpeaks.imagerepositoryapp.persistence.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Persistence {

    @Provides
    @Singleton
    fun provideImageDao(appDatabase: AppDatabase):ImageDao{
        return appDatabase.imageDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application):AppDatabase{
        return Room
            .databaseBuilder(application,AppDatabase::class.java,
            application.getString(R.string.database))
            .fallbackToDestructiveMigration()
            .build()
    }
}