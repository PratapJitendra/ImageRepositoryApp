package com.pivotalpeaks.imagerepositoryapp.detailsscreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pivotalpeaks.imagerepositoryapp.Image
import com.pivotalpeaks.imagerepositoryapp.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
):ViewModel() {

    private var _imageDetails:LiveData<Image> = MutableLiveData()
    val imageDetails:LiveData<Image> get() = _imageDetails

    @WorkerThread
    fun  getImage(id:Long){
        _imageDetails=detailsRepository.getImageById(id)
    }

    init {
        Log.d(TAG, ": initialized details view model")
    }
}