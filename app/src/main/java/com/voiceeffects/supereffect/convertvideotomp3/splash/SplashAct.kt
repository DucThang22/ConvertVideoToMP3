package com.voiceeffects.supereffect.convertvideotomp3.splash

import android.annotation.SuppressLint
import android.os.Bundle
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingActivity
import com.voiceeffects.supereffect.convertvideotomp3.databinding.SplashActBinding

@SuppressLint("CustomSplashScreen")
class SplashAct : BaseBindingActivity<SplashActBinding, SplashViewModel>(){
    override val layoutId: Int
        get() = R.layout.splash_act

    override fun getViewModel(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun setupView(savedInstanceState: Bundle?) {

    }

    override fun setupData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}