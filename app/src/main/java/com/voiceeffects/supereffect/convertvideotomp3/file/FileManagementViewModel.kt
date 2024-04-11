package com.voiceeffects.supereffect.convertvideotomp3.file

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseViewModel
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.AudioEntity
import com.voiceeffects.supereffect.convertvideotomp3.data.model.AudioModel
import com.voiceeffects.supereffect.convertvideotomp3.data.model.VideoModel
import com.voiceeffects.supereffect.convertvideotomp3.data.repository.AudioRepository
import com.voiceeffects.supereffect.convertvideotomp3.data.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileManagementViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val audioRepository: AudioRepository
) : BaseViewModel() {

    //video
    private val _listVideoByStorage = MutableLiveData<MutableList<VideoModel>>()
    val listVideoByStorage: LiveData<MutableList<VideoModel>>
        get() = _listVideoByStorage

    private val _listAudioByStorage = MutableLiveData<MutableList<AudioModel>>()

    //audio
    val listAudioByStorage: LiveData<MutableList<AudioModel>>
        get() = _listAudioByStorage


    var typeFile: Int?= 0

    fun getAllVideo() = viewModelScope.launch(Dispatchers.IO) {
        val listVideo = videoRepository.queryVideos()
        _listVideoByStorage.postValue(listVideo.toMutableList())
    }

//    fun getAllAudio(context: Context) = viewModelScope.launch(Dispatchers.IO) {
//        val listAudio = audioRepository.syncAudioFromExternalStorage(context)
//        val listAudioFull = mutableListOf<AudioModel>()
//        listAudioFull.addAll(listAudio)
//        _listAudioByStorage.postValue(listAudio)
//    }

    fun addAllAudio(audioEntity: AudioEntity) = viewModelScope.launch(Dispatchers.Default) {
        audioRepository.addAudio(audioEntity)
    }
}
