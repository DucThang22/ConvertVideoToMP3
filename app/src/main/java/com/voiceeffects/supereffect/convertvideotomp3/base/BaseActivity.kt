package com.voiceeffects.supereffect.convertvideotomp3.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.voiceeffects.supereffect.convertvideotomp3.utils.SystemUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        SystemUtil.setLocale(this)
        super.onCreate(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context) {
        SystemUtil.setLocale(newBase)
        super.attachBaseContext(newBase)
    }

    override fun onResume() {
        super.onResume()
        Log.e("TAG", "onResume: ", )
    }
}