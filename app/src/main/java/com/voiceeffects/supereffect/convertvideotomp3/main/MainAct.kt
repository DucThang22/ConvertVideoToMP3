package com.voiceeffects.supereffect.convertvideotomp3.main

import android.os.Bundle
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingActivity
import com.voiceeffects.supereffect.convertvideotomp3.databinding.MainActBinding

class MainAct : BaseBindingActivity<MainActBinding, MainViewModel>() {
    override val layoutId: Int
        get() = R.layout.main_act

    override fun getViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun setupView(savedInstanceState: Bundle?) {
        initViewPager()
    }

    private fun initViewPager() {

    }

    override fun setupData() {

    }

}