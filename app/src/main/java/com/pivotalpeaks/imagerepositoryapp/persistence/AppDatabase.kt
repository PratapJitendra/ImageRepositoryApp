package com.pivotalpeaks.imagerepositoryapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pivotalpeaks.imagerepositoryapp.Image

@Database(entities = [Image::class],version = 2,exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun imageDao():ImageDao
}