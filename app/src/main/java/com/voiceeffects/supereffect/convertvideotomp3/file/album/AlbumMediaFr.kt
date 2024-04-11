package com.voiceeffects.supereffect.convertvideotomp3.file.album

import android.os.Bundle
import android.view.View
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingFragment
import com.voiceeffects.supereffect.convertvideotomp3.databinding.AlbumMediaFrBinding

class AlbumMediaFr : BaseBindingFragment<AlbumMediaFrBinding, AlbumMediaViewModel>() {
    override fun getViewModel(): Class<AlbumMediaViewModel> = AlbumMediaViewModel::class.java

    override fun registerOnBackPress() {

    }

    override val layoutId: Int
        get() = R.layout.album_media_fr

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {

    }
}