package com.pivotalpeaks.imagerepositoryapp.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.pivotalpeaks.imagerepositoryapp.Image
import com.pivotalpeaks.imagerepositoryapp.network.ImageService
import com.pivotalpeaks.imagerepositoryapp.persistence.ImageDao
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val imageService: ImageService,
    private val imageDao: ImageDao
) {
    init {
       Log.d("image repository","Injecting main repository")
    }

    @WorkerThread
    fun loadImages(
        onStart:()->Unit,
        onSuccess:()->Unit,
        onError:(String) ->Unit
    )= flow {
        val images:List<Image> = imageDao.getImageList()

        if (images.isEmpty()){
            imageService.retrieveImageList()
                .suspendOnSuccess {
                    imageDao.insertImageList(data)
                    emit(data)
                }
                .onError { onError(message()) }
                .onException { onError(message()) }

        } else {
            emit(images)
        }


    }.onStart { onStart() }.onCompletion { onSuccess() }.flowOn(Dispatchers.IO)

}