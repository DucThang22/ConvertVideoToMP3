package com.voiceeffects.supereffect.convertvideotomp3.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingActivity
import com.voiceeffects.supereffect.convertvideotomp3.custom_view.CustomTooltipManager
import com.voiceeffects.supereffect.convertvideotomp3.databinding.MainActBinding
import com.voiceeffects.supereffect.convertvideotomp3.main.adapter.FragmentTabLayoutAdapter
import com.voiceeffects.supereffect.convertvideotomp3.setting.SettingAct

class MainAct : BaseBindingActivity<MainActBinding, MainViewModel>() {

    private val fragmentTabLayoutAdapter by lazy {
        FragmentTabLayoutAdapter(
            supportFragmentManager, lifecycle
        )
    }

    override val layoutId: Int
        get() = R.layout.main_act

    override fun getViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun setupView(savedInstanceState: Bundle?) {
        initAction()
        initViewPager()
        initTooltip()
    }

    private fun initTooltip() {
//        CustomTooltipManager.toggleTooltipFromView(
//            root,
//            root.context.getString(R.string.fractional_share_tool_tip_me_tab),
//            CustomTooltipManager.TooltipBehaviorModel(
//                anim = true,
//                dismissOutsideWithAnim = false,
//                backgroundColor = ContextCompat.getColor(
//                    root.context,
//                    R.color.colorFractionalSharesTooltip
//                ),
//                indicatorColor = ContextCompat.getColor(
//                    root.context,
//                    R.color.colorFractionalSharesTooltip
//                ),
//                textColor = Color.WHITE
//            )
//        )
    }

    private fun initAction() {
        binding.btnSetting.setOnClickListener {
            startActivity(SettingAct.getIntent(this))
        }
    }

    private fun initViewPager() {
        binding.viewPager.adapter = fragmentTabLayoutAdapter
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = 2
        binding.bottomNav.menu.forEach {
           binding.bottomNav.findViewById<View>(it.itemId).setOnLongClickListener {
               true
           }
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> binding.viewPager.setCurrentItem(0, false)
                R.id.product -> binding.viewPager.setCurrentItem(1, false)
            }
            true
        }
    }

    override fun setupData() {

    }

}