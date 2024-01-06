package com.voiceeffects.supereffect.convertvideotomp3.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingActivity
import com.voiceeffects.supereffect.convertvideotomp3.databinding.SettingActBinding

class SettingAct : BaseBindingActivity<SettingActBinding, SettingViewModel>() {
    override val layoutId: Int
        get() = R.layout.setting_act

    override fun getViewModel(): Class<SettingViewModel> = SettingViewModel::class.java

    override fun setupView(savedInstanceState: Bundle?) {
       initAction()
    }

    private fun initAction() {
        binding.backBt.setOnClickListener {
            finish()
        }
    }

    override fun setupData() {

    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SettingAct::class.java)
        }
    }

}