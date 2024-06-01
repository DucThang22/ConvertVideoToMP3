package com.voiceeffects.supereffect.convertvideotomp3.custom_view

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.voiceeffects.supereffect.convertvideotomp3.R
import java.lang.ref.WeakReference

object CustomTooltipManager : View.OnAttachStateChangeListener {
    private var tooltip: WeakReference<CustomTooltip>? = null

    override fun onViewAttachedToWindow(v: View) {
        // No action
    }

    override fun onViewDetachedFromWindow(v: View) {
        tooltip?.get()?.removeOnAttachStateChangeListener(this)
        tooltip?.clear()
        tooltip = null
    }

    fun toggleTooltipFromView(
        anchorView: View,
        text: String = "",
        tooltipBehaviorModel: TooltipBehaviorModel = TooltipBehaviorModel(
            backgroundColor = ContextCompat.getColor(
                anchorView.context,
                R.color.color_110620
            ),
            indicatorColor = ContextCompat.getColor(
                anchorView.context,
                R.color.color_292727
            ),
            textColor = Color.WHITE
        )
    ) {
        if (tooltip == null) {
            showTooltipFromView(
                anchorView,
                text,
                tooltipBehaviorModel
            )
        } else {
            hideTooltip(tooltipBehaviorModel.anim)
        }
    }

    private fun showTooltipFromView(
        anchorView: View,
        text: String,
        tooltipBehaviorModel: TooltipBehaviorModel
    ) {
        tooltip = getCustomTooltip(anchorView.context)
        tooltip?.get()?.withText(text)
            ?.withShowCloseButton(tooltipBehaviorModel.showCloseButton)
            ?.withTouchOutsideExceptAnchorRect(tooltipBehaviorModel.touchOutsideExceptAnchorView)
            ?.withMargin(tooltipBehaviorModel.margin)
            ?.dismissOutsideWithAnim(tooltipBehaviorModel.dismissOutsideWithAnim)
            ?.withDuration(tooltipBehaviorModel.duration)
            ?.withBackgroundColor(tooltipBehaviorModel.backgroundColor)
            ?.withIndicatorColor(tooltipBehaviorModel.indicatorColor)
            ?.withTextColor(tooltipBehaviorModel.textColor)
        tooltip?.get()?.addOnAttachStateChangeListener(this)
        tooltip?.get()?.show(anchorView, tooltipBehaviorModel.anim)
    }

    private fun hideTooltip(anim: Boolean) {
        tooltip?.get()?.dismiss(anim)
    }

    private fun getCustomTooltip(context: Context): WeakReference<CustomTooltip>? {
        return WeakReference(CustomTooltip(context))
    }

    data class TooltipBehaviorModel(
        val showCloseButton: Boolean = false,
        val touchOutsideExceptAnchorView: Boolean = true,
        val anim: Boolean = true,
        val margin: Int = 8,
        val dismissOutsideWithAnim: Boolean = true,
        val duration: Long = 150,
        val backgroundColor: Int = Color.WHITE,
        val indicatorColor: Int = Color.WHITE,
        val textColor: Int = Color.BLACK
    )
}