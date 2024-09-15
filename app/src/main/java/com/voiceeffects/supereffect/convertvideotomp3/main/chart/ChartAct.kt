package com.voiceeffects.supereffect.convertvideotomp3.main.chart

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseBindingActivity
import com.voiceeffects.supereffect.convertvideotomp3.databinding.ChartActBinding
import com.voiceeffects.supereffect.convertvideotomp3.main.MainViewModel

class ChartAct: BaseBindingActivity<ChartActBinding, MainViewModel>() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ChartAct::class.java)
        }
    }

    override val layoutId: Int
        get() = R.layout.chart_act

    override fun getViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun setupView(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.apply {
            legendRecyclerView.layoutManager = LinearLayoutManager(this@ChartAct)
            val legendItems = listOf(
                LegendAdapter.LegendItem(Color.RED, "Category 1", 40f),
                LegendAdapter.LegendItem(Color.BLUE, "Category 2", 30f),
                LegendAdapter.LegendItem(Color.GREEN, "Category 3", 20f),
                LegendAdapter.LegendItem(Color.YELLOW, "Category 4", 10f)
            )
            legendRecyclerView.adapter = LegendAdapter(legendItems)


            val segments = listOf(
                DoughnutChartView.Segment(Color.RED, 60f),
                DoughnutChartView.Segment(Color.RED, 50f),
                DoughnutChartView.Segment(Color.RED, 40f),
                DoughnutChartView.Segment(Color.BLUE, 30f),
                DoughnutChartView.Segment(Color.GREEN, 20f),
                DoughnutChartView.Segment(Color.YELLOW, 100f)
            )

            val maxWidth = resources.getDimensionPixelSize(R.dimen.segment_max_width)
            seaRecyclerView.layoutManager = LinearLayoutManager(this@ChartAct)
            seaRecyclerView.adapter = SegmentAdapter(segments, maxWidth)

        }
    }

    override fun setupData() {}
}