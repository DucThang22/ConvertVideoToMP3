package com.voiceeffects.supereffect.convertvideotomp3.permission.dialog

import android.os.Bundle
import android.view.View
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingDialogFragment
import com.voiceeffects.supereffect.convertvideotomp3.databinding.DialogDeniedPermissionBinding
import com.voiceeffects.supereffect.convertvideotomp3.utils.preventTwoClick

class DeniedPermissionDialogFragment: BaseBindingDialogFragment<DialogDeniedPermissionBinding>() {

    var onClickCancel: (() -> Unit) = {}
    var onClickGotoSetting: (() -> Unit) = {}
    override val layoutId: Int
        get() = R.layout.dialog_denied_permission

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAction()
    }

    private fun initAction() {
        binding.tvCancel.setOnClickListener {
            it.preventTwoClick(500)
            onClickCancel()
            dismiss()
        }

        binding.btnOk.setOnClickListener {
            it.preventTwoClick(500)
            onClickGotoSetting()
            dismiss()
        }
    }

}