package com.voiceeffects.supereffect.convertvideotomp3.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.voiceeffects.supereffect.convertvideotomp3.main.home.HomeFr
import com.voiceeffects.supereffect.convertvideotomp3.main.product.ProductFr

class FragmentTabLayoutAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle){

    private val homeFr: HomeFr by lazy { HomeFr() }
    private val productFr: ProductFr by lazy { ProductFr() }
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
             1 -> return productFr
        }
        return homeFr
    }
}