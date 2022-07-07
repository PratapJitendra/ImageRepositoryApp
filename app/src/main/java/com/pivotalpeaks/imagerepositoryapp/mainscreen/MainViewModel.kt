package com.pivotalpeaks.imagerepositoryapp.mainscreen

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.pivotalpeaks.imagerepositoryapp.Image
import com.pivotalpeaks.imagerepositoryapp.extensions.viewModelIOContext
import com.pivotalpeaks.imagerepositoryapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) :ViewModel() {
    private val _imageList:MutableLiveData<Boolean> = MutableLiveData(true)
    private val _isLoading:MutableLiveData<Boolean> = MutableLiveData(false)
    private val _toast:MutableLiveData<String> = MutableLiveData()

    val imageList:LiveData<List<Image>> = _imageList.switchMap {
        this.mainRepository.loadImages(
            onStart = {_isLoading.postValue(true)},
            onSuccess = {_isLoading.postValue(false)},
            onError = {_toast.postValue(it)}
        ).asLiveData(viewModelIOContext)
    }

    val toast:LiveData<String> get() = _toast

    private val _selectTab:MutableState<Int> = mutableStateOf(0)
    val selectTab: State<Int> get() = _selectTab

    val isLoading:LiveData<Boolean> get() = _isLoading

    @MainThread
    fun selectTab(@StringRes tab:Int){
        _selectTab.value=tab
    }

    init {
        Log.d( "view model ","view model initialized")
    }
}