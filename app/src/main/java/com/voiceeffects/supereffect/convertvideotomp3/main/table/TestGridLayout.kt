package com.voiceeffects.supereffect.convertvideotomp3.main.table

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingActivity
import com.voiceeffects.supereffect.convertvideotomp3.databinding.TestActivityBinding
import com.voiceeffects.supereffect.convertvideotomp3.main.MainViewModel

class TestGridLayout: BaseBindingActivity<TestActivityBinding, MainViewModel>() {
    override val layoutId: Int
        get() = R.layout.test_activity

    override fun getViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun setupView(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.apply {
            gridLayout.columnCount = 3
            gridLayout.rowCount = 3

            val numbers = arrayOf(
                arrayOf(1, 2, 3),
                arrayOf(4, 5, 6),
                arrayOf(7, 8, 9)
            )

            for (row in numbers.indices) {
                for (col in numbers[row].indices) {
                    val textView = TextView(applicationContext).apply {
                        text = numbers[row][col].toString()
                        textSize = 24f
                        setPadding(16, 16, 16, 16)
                        gravity = Gravity.CENTER
                    }

                    val param = GridLayout.LayoutParams().apply {
                        rowSpec = GridLayout.spec(row, 1f)
                        columnSpec = GridLayout.spec(col, 1f)
                    }

                    textView.layoutParams = param
                    gridLayout.addView(textView)
                }
            }
        }
    }

    override fun setupData() {

    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, TestGridLayout::class.java)
        }
    }

}