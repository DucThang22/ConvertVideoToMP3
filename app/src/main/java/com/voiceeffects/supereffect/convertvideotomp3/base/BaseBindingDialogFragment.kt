package com.voiceeffects.supereffect.convertvideotomp3.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingDialogFragment<B : ViewDataBinding> : BaseDialogFragment() {
    protected lateinit var binding: B
    protected abstract val layoutId: Int
    protected abstract fun onCreatedView(view: View?, savedInstanceState: Bundle?)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreatedView(view, savedInstanceState)
    }


    private var toast: Toast? = null

    private var handlerToast = Handler(Looper.getMainLooper())
    fun toast(text: String) {
        toast?.cancel()
        toast = Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT)
        toast?.show()

        handlerToast.postDelayed({
            toast?.cancel()
        }, 1500)
    }


    override fun onDestroy() {
        super.onDestroy()
        toast?.cancel()
        handlerToast.removeCallbacksAndMessages(null)
    }
}