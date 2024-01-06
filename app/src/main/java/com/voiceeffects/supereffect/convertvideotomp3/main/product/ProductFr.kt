package com.voiceeffects.supereffect.convertvideotomp3.main.product

import android.os.Bundle
import android.view.View
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingFragment
import com.voiceeffects.supereffect.convertvideotomp3.databinding.ProductFrBinding

class ProductFr : BaseBindingFragment<ProductFrBinding, ProductViewModel>(){
    override fun getViewModel(): Class<ProductViewModel> = ProductViewModel::class.java

    override fun registerOnBackPress() {

    }

    override val layoutId: Int
        get() = R.layout.product_fr

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {

    }
}