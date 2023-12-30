package com.voiceeffects.supereffect.convertvideotomp3.main.home

import android.os.Bundle
import android.view.View
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingFragment
import com.voiceeffects.supereffect.convertvideotomp3.databinding.HomeFrBinding

class HomeFr : BaseBindingFragment<HomeFrBinding, HomeViewModel>() {
    override fun getViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun registerOnBackPress() {

    }

    override val layoutId: Int
        get() = R.layout.home_fr

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {

    }
}