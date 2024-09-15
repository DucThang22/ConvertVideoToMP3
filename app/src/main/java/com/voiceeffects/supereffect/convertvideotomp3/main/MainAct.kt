package com.voiceeffects.supereffect.convertvideotomp3.main

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.forEach
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingActivity
import com.voiceeffects.supereffect.convertvideotomp3.databinding.MainActBinding
import com.voiceeffects.supereffect.convertvideotomp3.main.adapter.FragmentTabLayoutAdapter
import com.voiceeffects.supereffect.convertvideotomp3.main.chart.ChartAct
import com.voiceeffects.supereffect.convertvideotomp3.main.table.TestGridLayout
import com.voiceeffects.supereffect.convertvideotomp3.utils.ThemeApp

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
            startActivity(ChartAct.getIntent(this))
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

    fun changeThemeApp(theme: String) {
        if (theme == ThemeApp.Dark.name) {
            turnOnNightTheme()
//            settingGeneralApp.theme = ThemeApp.Dark
        } else {
            turnOffNightTheme()
//            settingGeneralApp.theme = ThemeApp.Light
        }
    }


    private fun turnOffNightTheme() {
        // is light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window?.decorView?.let {
                it.windowInsetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        } else {
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun turnOnNightTheme() {
        // is night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window?.decorView?.let {
                it.windowInsetsController?.setSystemBarsAppearance(
                    0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        } else {
            window?.decorView?.systemUiVisibility = 0
        }
    }

}