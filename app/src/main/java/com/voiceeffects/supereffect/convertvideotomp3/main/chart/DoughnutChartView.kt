package com.voiceeffects.supereffect.convertvideotomp3.main.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class DoughnutChartView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 50f // độ dày của vòng tròn
    }

    private val segments = listOf(
        Segment(Color.RED, 40f), // 40%
        Segment(Color.BLUE, 30f), // 30%
        Segment(Color.GREEN, 20f), // 20%
        Segment(Color.YELLOW, 10f) // 10%
    )

    private val segmentSpacing = 1f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val offset = 180f // Khoảng cách cần di chuyển lên
        val rectF = RectF(290f, 290f - offset, width - 290f, width - 290f - offset)
        var startAngle = -90f

        for (segment in segments) {
            paint.color = segment.color
            val sweepAngle = segment.percentage / 100 * 360 - segmentSpacing
            canvas.drawArc(rectF, startAngle, sweepAngle, false, paint)
            startAngle += sweepAngle + segmentSpacing
        }
    }

    data class Segment(val color: Int, val percentage: Float)
}