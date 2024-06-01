package com.voiceeffects.supereffect.convertvideotomp3.file.all

import android.os.Bundle
import android.view.View
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingFragment
import com.voiceeffects.supereffect.convertvideotomp3.databinding.AllMediaFrBinding
import com.voiceeffects.supereffect.convertvideotomp3.file.FileManagementViewModel
import com.voiceeffects.supereffect.convertvideotomp3.file.adapter.AudioAdapter

class AllMediaFr : BaseBindingFragment<AllMediaFrBinding, FileManagementViewModel>() {
    override fun getViewModel(): Class<FileManagementViewModel> =
        FileManagementViewModel::class.java

    override fun registerOnBackPress() {

    }

    override val layoutId: Int
        get() = R.layout.all_media_fr

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()
        initView()
        initObserve()
    }

    private fun initData() {
        viewModel.getAllVideo()
    }

    private fun initObserve() {
        viewModel.listVideoByStorage.observe(viewLifecycleOwner) {
            it.map {

            }
        }
        viewModel.listAudioByStorage.observe(this) {

        }
    }

    private val adapter: AudioAdapter by lazy {
        AudioAdapter(requireContext()).apply {
            onSelect = { pos, audioEntity ->

            }
            onPlayAudio = { pos, audioEntity ->

            }
        }
    }

    private fun initView() {
        if(viewModel.typeFile == 1) {
            binding.videoView.recycleVideo.visibility = View.VISIBLE
            binding.recycleAudio.visibility = View.GONE
        } else {
            binding.videoView.recycleVideo.visibility = View.GONE
            binding.recycleAudio.visibility = View.VISIBLE
        }
    }
}