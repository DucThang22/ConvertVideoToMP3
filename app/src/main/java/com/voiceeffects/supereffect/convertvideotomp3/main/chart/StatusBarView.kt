package com.voiceeffects.supereffect.convertvideotomp3.main.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class StatusBarView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var cashPercentage: Float = 45f
    private var marketMoneyPercentage: Float = 40f

    private val cashColor = Color.BLUE
    private val marketMoneyColor = Color.RED
    private val paint = Paint()
    private val textPaint = Paint()
    private val textSize = 24f
    private val totalBarWidth = dpToPx(200f) // Đặt chiều dài của view là 200dp

    init {
        // Cài đặt màu và kiểu cho các phần tử vẽ
        paint.isAntiAlias = true
        paint.isDither = true

        // Cài đặt kiểu và màu cho văn bản
        textPaint.color = Color.BLACK
        textPaint.textSize = textSize
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
    }

    fun setData(cashPercentage: Float, marketMoneyPercentage: Float) {
        this.cashPercentage = cashPercentage
        this.marketMoneyPercentage = marketMoneyPercentage
        invalidate() // Yêu cầu vẽ lại view
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = totalBarWidth.toFloat() // Chiều rộng thanh trạng thái được giới hạn ở 200dp
        val height = height.toFloat()

        // Tính tổng phần trăm và đảm bảo nó không vượt quá 100%
        val totalPercentage = cashPercentage + marketMoneyPercentage

        // Tính toán chiều rộng của từng phần dựa trên tỷ lệ phần trăm
        val totalWidth = width * (totalPercentage / 100f)
        val cashWidth = totalWidth * (cashPercentage / totalPercentage)
        val marketMoneyWidth = totalWidth * (marketMoneyPercentage / totalPercentage)

        // Vẽ phần cash
        paint.color = cashColor
        canvas.drawRect(0f, 0f, cashWidth, height, paint)

        // Vẽ phần market money
        paint.color = marketMoneyColor
        canvas.drawRect(cashWidth, 0f, cashWidth + marketMoneyWidth, height, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Đặt chiều rộng của view là 200dp và giữ nguyên chiều cao được đo
        val desiredWidth = totalBarWidth
        setMeasuredDimension(desiredWidth, measuredHeight)
    }

    // Chuyển đổi dp thành px
    private fun dpToPx(dp: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
}
