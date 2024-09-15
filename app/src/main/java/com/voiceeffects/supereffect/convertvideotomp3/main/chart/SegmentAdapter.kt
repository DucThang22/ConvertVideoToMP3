package com.voiceeffects.supereffect.convertvideotomp3.main.chart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.voiceeffects.supereffect.convertvideotomp3.R

class SegmentAdapter(private val segments: List<DoughnutChartView.Segment>, private val maxWidth: Int) :
    RecyclerView.Adapter<SegmentAdapter.SegmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SegmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_segment, parent, false)
        return SegmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: SegmentViewHolder, position: Int) {
        val segment = segments[position]
        holder.bind(segment)
    }

    override fun getItemCount(): Int {
        return segments.size
    }

    inner class SegmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSegmentName: TextView = itemView.findViewById(R.id.tvSegmentName)
        private val vSegmentProgress: View = itemView.findViewById(R.id.vSegmentProgress)
        private val tvSegmentPercentage: TextView = itemView.findViewById(R.id.tvSegmentPercentage)

        fun bind(segment: DoughnutChartView.Segment) {
            tvSegmentName.text = "Segment ${adapterPosition + 1}" // Tên segment
            tvSegmentPercentage.text = "${segment.percentage}%"

            // Điều chỉnh chiều rộng của thanh trạng thái dựa trên tỷ lệ phần trăm
            val params = vSegmentProgress.layoutParams
            params.width = (segment.percentage / 100 * maxWidth).toInt() // maxWidth là chiều rộng tối đa của thanh
            vSegmentProgress.layoutParams = params
        }
    }
}