package com.voiceeffects.supereffect.convertvideotomp3.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }
}

fun <T> MutableLiveData<List<T>>.toMutableList(transform: (T) -> T): MutableList<T>? {
    return this.value?.map { transform(it) }?.toMutableList()
}