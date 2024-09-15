package com.voiceeffects.supereffect.convertvideotomp3.main.table

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.GridLayout

class BorderedGridLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 1f // Độ dày của đường viền
        style = Paint.Style.STROKE
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 40f // Kích thước chữ
        textAlign = Paint.Align.CENTER
    }

    private val textMargin = 16 * context.resources.displayMetrics.density
    private val textMarginRight = 20 * context.resources.displayMetrics.density
    private val bottomText = arrayOf("Text1", "Text2", "Text3")
    private val rightText = arrayOf("Text4", "Text5", "Text6")

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)

        val childCount = childCount
        val columns = columnCount
        val rows = rowCount
        val strokeWidth = paint.strokeWidth
        val halfStrokeWidth = strokeWidth / 2

        // Vẽ viền cho từng ô
        for (i in 0 until childCount) {
            val child = getChildAt(i)

            // Tính toán các tọa độ
            val left = child.left.toFloat() - halfStrokeWidth
            val top = child.top.toFloat() - halfStrokeWidth
            val right = child.right.toFloat() + halfStrokeWidth
            val bottom = child.bottom.toFloat() + halfStrokeWidth

            // Vẽ viền cho từng ô
            canvas.drawRect(left, top, right, bottom, paint)
        }

        // Vẽ viền giữa các ô
        for (i in 0 until childCount) {
            val child = getChildAt(i)

            // Lấy vị trí hàng và cột từ chỉ số của View
            val column = i % columns
            val row = i / columns

            // Vẽ viền phải cho ô không phải ở cột cuối cùng
            if (column < columns - 1) {
                canvas.drawLine(
                    (child.right + halfStrokeWidth),
                    child.top.toFloat(),
                    (child.right + halfStrokeWidth),
                    child.bottom.toFloat(),
                    paint
                )
            }

            // Vẽ viền dưới cho ô không phải ở hàng cuối cùng
            if (row < rows - 1) {
                canvas.drawLine(
                    child.left.toFloat(),
                    (child.bottom + halfStrokeWidth),
                    child.right.toFloat(),
                    (child.bottom + halfStrokeWidth),
                    paint
                )
            }
        }

        // Vẽ văn bản dưới các ô cạnh dưới
        for (i in 0 until columns) {
            val child = getChildAt((rows - 1) * columns + i)
            val x = child.left + (child.right - child.left) / 2
            val y = child.bottom + textMargin // Khoảng cách từ đáy ô đến văn bản
            canvas.drawText(bottomText[i], x.toFloat(), y.toFloat(), textPaint)
        }

        // Vẽ văn bản bên cạnh các ô cạnh phải
        for (i in 0 until rows) {
            val child = getChildAt(i * columns + (columns - 1))
            val x = child.right + textMarginRight // Khoảng cách từ cạnh ô đến văn bản
            val y = child.top + (child.bottom - child.top) / 2
            canvas.drawText(rightText[i], x.toFloat(), y.toFloat(), textPaint)
        }
    }
}