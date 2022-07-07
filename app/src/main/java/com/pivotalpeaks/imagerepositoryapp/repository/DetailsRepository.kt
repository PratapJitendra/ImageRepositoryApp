package com.pivotalpeaks.imagerepositoryapp.repository

import com.pivotalpeaks.imagerepositoryapp.persistence.ImageDao
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val imageDao: ImageDao
) {
    fun getImageById(id:Long)= imageDao.getImage(id)
}