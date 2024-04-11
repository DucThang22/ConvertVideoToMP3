package com.voiceeffects.supereffect.convertvideotomp3.file

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingActivity
import com.voiceeffects.supereffect.convertvideotomp3.databinding.FileManagementActBinding
import com.voiceeffects.supereffect.convertvideotomp3.file.album.AlbumMediaFr
import com.voiceeffects.supereffect.convertvideotomp3.file.all.AllMediaFr
import java.util.Locale

class FileManagementAct: BaseBindingActivity<FileManagementActBinding, FileManagementViewModel>() {
    override val layoutId: Int
        get() = R.layout.file_management_act

    override fun getViewModel(): Class<FileManagementViewModel> = FileManagementViewModel::class.java

    override fun setupView(savedInstanceState: Bundle?) {
        initViewPager()
        initAction()
    }

    private fun initAction() {
        binding.backBt.setOnClickListener {
            finish()
        }
    }

    private fun initViewPager() {
        binding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        binding.apply {
            if (viewPager.adapter == null) {
                viewPager.isUserInputEnabled = true
                viewPager.isSaveEnabled = false
                viewPager.offscreenPageLimit = 2
                viewPager.adapter = object : FragmentStateAdapter(this@FileManagementAct) {

                    override fun getItemCount(): Int {
                        return 2
                    }

                    override fun createFragment(position: Int): Fragment {
                        return when (position) {
                            0 -> AllMediaFr()

                            1 -> AlbumMediaFr()
                            else -> throw IllegalStateException(
                                "ViewPage position $position"
                            )
                        }
                    }
                }
                TabLayoutMediator(
                    tabLayout,
                    viewPager
                ) { tab, position ->
                    tab.text = when (position) {
                        0 -> getString(R.string.string_all)
                        1 -> getString(R.string.string_album)
                        else -> ""
                    }
                }.attach()
                intent?.apply {
                    viewPager.currentItem = this.getIntExtra(CURRENT_ITEM, 0)
                }
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.text.toString().lowercase()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                tab?.text.toString().lowercase()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
//                tab?.text.toString().lowercase()
            }

        })
    }

    override fun setupData() {
        intent.getIntExtra(TYPE_FILE, 0).let {
            viewModel.typeFile = it
        }
//        viewModel.getAllAudio(this)
//        viewModel.getAllVideo(this)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val CURRENT_ITEM = "FileManagementAct-Current_Item"
        private const val TYPE_FILE = "FileManagementAct-type_file"

        fun getIntent(context: Context, currentItem: Int, typeFile: Int): Intent {
            val intent = Intent(context, FileManagementAct::class.java)
            intent.putExtra(CURRENT_ITEM, currentItem)
            intent.putExtra(TYPE_FILE, typeFile)
            return intent
        }
    }
}