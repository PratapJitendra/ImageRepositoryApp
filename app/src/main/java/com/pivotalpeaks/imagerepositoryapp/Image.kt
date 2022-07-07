package com.pivotalpeaks.imagerepositoryapp

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Immutable
data class Image(
    @PrimaryKey val id: Long,
    val title: String,
    val imageurl: String,
    val height: String,
    val desc:String
)