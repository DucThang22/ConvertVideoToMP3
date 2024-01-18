package com.voiceeffects.supereffect.convertvideotomp3.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingActivity
import com.voiceeffects.supereffect.convertvideotomp3.databinding.PermissionActBinding
import com.voiceeffects.supereffect.convertvideotomp3.main.MainAct
import com.voiceeffects.supereffect.convertvideotomp3.permission.dialog.DeniedPermissionDialogFragment
import com.voiceeffects.supereffect.convertvideotomp3.utils.gone
import com.voiceeffects.supereffect.convertvideotomp3.utils.isGrantAudio
import com.voiceeffects.supereffect.convertvideotomp3.utils.preventTwoClick
import com.voiceeffects.supereffect.convertvideotomp3.utils.visible

class PermissionAct : BaseBindingActivity<PermissionActBinding, PermissionViewModel>() {
    private var isGrantMedia = false
        set(value) {
            field = value
            binding.tvMediaPermission.setCompoundDrawablesWithIntrinsicBounds(
                0, 0, if (value) {
                    R.drawable.ic_switch_on_v2
                } else {
                    R.drawable.ic_switch_off_v2
                }, 0
            )
            if (isGrantMedia) {
                binding.btnNext.visible()
            }

            if (value && dialogDeniedLocation.isAdded) {
                dialogDeniedLocation.dismiss()
            }
        }

    private val dialogDeniedNotification: DeniedPermissionDialogFragment by lazy {
        DeniedPermissionDialogFragment().apply {
            onClickGotoSetting = {
//                AppOpenManager.getInstance().disableAppResumeWithActivity(PermissionAct::class.java)
                startSetting()
            }
            onClickCancel = {
                dismiss()
            }
        }
    }

    private var requestPermissionReadExternal =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                isGrantMedia = true
                binding.tvMediaPermission.isEnabled = false
            } else {
                dialogDeniedLocation.show(supportFragmentManager, null)
            }
        }

    private val dialogDeniedLocation: DeniedPermissionDialogFragment by lazy {
        DeniedPermissionDialogFragment().apply {
            onClickGotoSetting = {
//                AppOpenManager.getInstance().disableAppResumeWithActivity(PermissionAct::class.java)
                startSetting()
            }
            onClickCancel = {
                dismiss()
            }
        }
    }

    override val layoutId: Int
        get() = R.layout.permission_act

    override fun getViewModel() = PermissionViewModel::class.java

    override fun setupView(savedInstanceState: Bundle?) {
        isGrantMedia = isGrantAudio()
        if (Build.VERSION.SDK_INT < 33) {
            binding.tvMediaPermission.gone()
        }
        if (isGrantMedia) {
            Intent(this, MainAct::class.java).apply {
                startActivity(this)
            }
        }
        initListener()
    }

    private fun initListener() {
        binding.btnNext.setOnClickListener {
            it.preventTwoClick(600)
            Intent(this, MainAct::class.java).apply {
                startActivity(this)
            }
        }

        binding.tvMediaPermission.setOnClickListener {
            try {
                it.preventTwoClick(600)
                if (!isGrantMedia) {
                    requestPermissionReadExternal.launch(if (Build.VERSION.SDK_INT < 33) Manifest.permission.READ_EXTERNAL_STORAGE else Manifest.permission.READ_MEDIA_AUDIO)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private var timeClick = 0L

    override fun onPause() {
        super.onPause()
    }
    override fun onResume() {
        super.onResume()
        try {
            isGrantMedia = isGrantAudio()
//            AppOpenManager.getInstance().enableAppResumeWithActivity(PermissionAct::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun setupData() {}

    private fun startSetting() = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
        startActivity(this)
    }
}