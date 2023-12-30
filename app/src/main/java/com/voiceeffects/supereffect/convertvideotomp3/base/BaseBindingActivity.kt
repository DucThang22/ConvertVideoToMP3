package com.voiceeffects.supereffect.convertvideotomp3.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

abstract class BaseBindingActivity<B : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {
    lateinit var binding: B
    lateinit var viewModel: VM

    abstract val layoutId: Int
    abstract fun getViewModel(): Class<VM>
    abstract fun setupView(savedInstanceState: Bundle?)
    abstract fun setupData()

    private fun Window.setLightStatusBars(b: Boolean) {
        WindowCompat.getInsetsController(this, decorView).isAppearanceLightStatusBars = b
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = ViewModelProvider(this)[getViewModel()]
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.setLightStatusBars(true)
        setupView(savedInstanceState)
        setupData()
    }

    override fun onStop() {
        super.onStop()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    private var toast: Toast? = null

    private var handlerToast = Handler(Looper.getMainLooper())
    fun toast(text: String) {
        toast?.cancel()
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast?.show()

        handlerToast.postDelayed({
            toast?.cancel()
        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handlerToast.removeCallbacksAndMessages(null)
    }

    override fun onResume() {
        super.onResume()

    }
}