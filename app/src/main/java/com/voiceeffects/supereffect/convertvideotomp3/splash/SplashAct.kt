package com.voiceeffects.supereffect.convertvideotomp3.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingActivity
import com.voiceeffects.supereffect.convertvideotomp3.databinding.SplashActBinding
import com.voiceeffects.supereffect.convertvideotomp3.main.MainAct
import com.voiceeffects.supereffect.convertvideotomp3.permission.PermissionAct
import com.voiceeffects.supereffect.convertvideotomp3.utils.SharePrefUtils
import com.voiceeffects.supereffect.convertvideotomp3.utils.SystemUtil

@SuppressLint("CustomSplashScreen")
class SplashAct : BaseBindingActivity<SplashActBinding, SplashViewModel>(){
    override val layoutId: Int
        get() = R.layout.splash_act

    override fun getViewModel(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun setupView(savedInstanceState: Bundle?) {
       binding.splashView.postDelayed({
           SystemUtil.setPreLanguage(this, SystemUtil.getPreLanguage(this))
           SystemUtil.setLocale(this)
           openMainActivity()
       }, 3000)
    }

    override fun setupData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun openMainActivity() {
        if (SharePrefUtils.showPermission(this)) {
            startActivity(Intent(this, PermissionAct::class.java))
        } else if (!SharePrefUtils.showPermission(this)) {
            startActivity(Intent(this, MainAct::class.java))
        }
        finish()
    }

}