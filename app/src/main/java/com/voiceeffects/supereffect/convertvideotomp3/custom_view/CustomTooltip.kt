package com.voiceeffects.supereffect.convertvideotomp3.custom_view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.voiceeffects.supereffect.convertvideotomp3.R
import com.voiceeffects.supereffect.convertvideotomp3.databinding.LayoutTooltipCustomBinding
import com.voiceeffects.supereffect.convertvideotomp3.utils.getLocationRectInWindow
import kotlin.math.roundToInt

class CustomTooltip @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private var binding: LayoutTooltipCustomBinding? = null

    private var heightScreen =
        Resources.getSystem().displayMetrics.heightPixels - context.resources.getDimensionPixelSize(
            R.dimen.dp1
        )
    private var leftMargin: Float = 16f
    private var rightMargin: Float = 16f
    private var margin = 8
    private var anchorRect = Rect()
    private var touchOutsideExceptAnchorRect = true
    private var dismissOutsideWithAnim = false
    private var duration: Long = 150
    private var backgroundColor: Int =
        ContextCompat.getColor(context, R.color.color_F04040)
    private var indicatorColor: Int =
        ContextCompat.getColor(context, R.color.color_110620)
    private var textColor: Int = Color.WHITE

    var isTooltipShowing = false
        protected set(value) {
            field = value
        }
    var isTooltipDismissing = false
        protected set(value) {
            field = value
        }

    init {
        initLayout()
    }

    fun withText(text: String): CustomTooltip {
        getContentTextView()?.text = text
        return this
    }

    fun withShowCloseButton(showCloseButton: Boolean = false): CustomTooltip {
        binding?.ivClose?.isVisible = showCloseButton
        return this
    }

    fun withTouchOutsideExceptAnchorRect(value: Boolean): CustomTooltip {
        touchOutsideExceptAnchorRect = value
        return this
    }

    fun withMargin(value: Int): CustomTooltip {
        margin = value
        return this
    }

    fun withDuration(value: Long): CustomTooltip {
        duration = value
        return this
    }

    fun withBackgroundColor(value: Int): CustomTooltip {
        backgroundColor = value
        return this
    }

    fun withTextColor(value: Int): CustomTooltip {
        textColor = value
        return this
    }

    fun withIndicatorColor(value: Int): CustomTooltip {
        indicatorColor = value
        return this
    }

    fun dismissOutsideWithAnim(value: Boolean): CustomTooltip {
        dismissOutsideWithAnim = value
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    open fun initLayout() {
        binding = LayoutTooltipCustomBinding.inflate(LayoutInflater.from(context), this, true)
        binding?.rootLayout?.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                dismiss(dismissOutsideWithAnim)
                anchorRect.contains(
                    event.x.roundToInt(),
                    event.y.roundToInt()
                ) && touchOutsideExceptAnchorRect
            } else {
                false
            }
        }
    }

    open fun isShowOnTop(anchorRect: Rect) =
        anchorRect.bottom + getContentLayoutHeight() > heightScreen

    open fun initTooltipIndicator(isShowOnTop: Boolean) {
        binding?.indicatorUp?.isVisible = !isShowOnTop
        binding?.indicatorDown?.isVisible = isShowOnTop
    }

    open fun calculateTooltipLocation(anchorRect: Rect, isShowOnTop: Boolean): PointF {
        val location = PointF()
        location.y = if (isShowOnTop) {
            anchorRect.top - getContentLayoutHeight().toFloat() - margin
        } else {
            anchorRect.bottom.toFloat() + margin + getTooltipIndicatorHeight(isShowOnTop)
        }
        location.x = anchorRect.centerX() - getContentLayoutWidth() / 2f
        if (location.x < leftMargin) {
            location.x = leftMargin
        } else if (location.x + getContentLayoutWidth() > width - rightMargin) {
            location.x = width - rightMargin - getContentLayoutWidth()
        }
        return location
    }

    open fun getContentLayout(): View? = binding?.layoutContent

    open fun getContentTextView(): TextView? = binding?.tvContent

    open fun getContentLayoutWidth(): Int = binding?.layoutContent?.width ?: 0

    open fun getContentLayoutHeight(): Int = binding?.layoutContent?.height ?: 0

    open fun updateUIColor() {
        val backgroundView = getContentLayout()
        backgroundView?.post {
            backgroundView.background?.let { background ->
                val newBackground = background.mutate()
                newBackground.colorFilter =
                    PorterDuffColorFilter(backgroundColor, PorterDuff.Mode.SRC_IN)
            }
            binding?.tvContent?.setTextColor(textColor)
            binding?.indicatorUp?.setColorFilter(indicatorColor)
            binding?.indicatorDown?.setColorFilter(indicatorColor)
        }
    }

    fun dismiss(anim: Boolean = true) {
        if (isTooltipDismissing) return
        isTooltipDismissing = true
        if (anim) {
            performDismissAnimation()
        } else {
            removeViewFromParent()
            isTooltipDismissing = false
        }
    }

    fun show(anchorView: View, withAnim: Boolean = true) {
        if (isTooltipShowing) return
        anchorView.post {
            isTooltipShowing = true
            anchorRect = anchorView.getLocationRectInWindow()
            val rootRect =
                (anchorView.rootView.findViewById(android.R.id.content) as View).getLocationRectInWindow()
            val topOffset = rootRect.top
            anchorRect.offset(0, -topOffset)
            (anchorView.rootView.findViewById(android.R.id.content) as? ViewGroup)?.addView(this)
            viewTreeObserver.addOnPreDrawListener(OnPreDrawListener(withAnim))
        }
    }

    protected fun getTooltipIndicator(isShowOnTop: Boolean): View? {
        return if (isShowOnTop) binding?.indicatorDown else binding?.indicatorUp
    }

    protected fun getTooltipIndicatorHeight(isShowOnTop: Boolean): Int {
        return getTooltipIndicator(isShowOnTop)?.height ?: 0
    }

    private fun setupInitialPosition() {
        val isShowOnTop = isShowOnTop(anchorRect)
        initTooltipIndicator(isShowOnTop)
        val tooltipIndicator = getTooltipIndicator(isShowOnTop)
        tooltipIndicator?.post {
            configTooltipIndicatorLocation(
                getTooltipIndicator(isShowOnTop),
                anchorRect,
                isShowOnTop
            )
            val location = calculateTooltipLocation(anchorRect, isShowOnTop)
            val layoutContent = getContentLayout()
            layoutContent?.x = location.x
            layoutContent?.y = location.y
        }
    }

    private fun removeViewFromParent() {
        (parent as? ViewGroup)?.removeView(this)
    }

    private fun configTooltipIndicatorLocation(
        tooltipIndicator: View?,
        anchorViewRect: Rect,
        isShowOnTop: Boolean
    ) {
        if (tooltipIndicator == null) return
        tooltipIndicator.alpha = 0f
        tooltipIndicator.post {
            tooltipIndicator.x =
                anchorViewRect.left + anchorViewRect.width() / 2f - tooltipIndicator.width / 2f
            tooltipIndicator.y =
                if (isShowOnTop) anchorViewRect.top - margin.toFloat() else anchorViewRect.bottom + margin.toFloat()
            tooltipIndicator.post {
                tooltipIndicator.alpha = 1f
            }
        }
    }

    private fun performShowAnimation() {
        val animateView = getContentLayout()
        val isShowOnTop = isShowOnTop(anchorRect)
        animateView?.alpha = 0f
        animateView?.animate()
            ?.alpha(1f)
            ?.setDuration(duration)
            ?.setInterpolator(LinearInterpolator())
            ?.setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    val indicator = getTooltipIndicator(isShowOnTop)
                    indicator?.alpha = 0f
                    indicator?.animate()?.alpha(1f)?.setDuration(duration)
                        ?.setInterpolator(LinearInterpolator())
                }

                override fun onAnimationEnd(animation: Animator) {
                    isTooltipShowing = false
                }
            })
    }

    private fun performDismissAnimation() {
        val animateView = getContentLayout()
        val isShowOnTop = isShowOnTop(anchorRect)
        animateView?.animate()
            ?.alpha(0f)
            ?.setDuration(duration)
            ?.setInterpolator(LinearInterpolator())
            ?.setStartDelay(100)
            ?.setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    val indicator = getTooltipIndicator(isShowOnTop)
                    indicator?.animate()?.alpha(0f)?.setDuration(duration)
                        ?.setInterpolator(LinearInterpolator())
                }

                override fun onAnimationEnd(animation: Animator) {
                    removeViewFromParent()
                    isTooltipDismissing = false
                }
            })
    }

    inner class OnPreDrawListener(val anim: Boolean) : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver?.removeOnPreDrawListener(this)
            updateUIColor()
            setupInitialPosition()
            if (anim) {
                performShowAnimation()
            } else {
                isTooltipShowing = false
            }
            return false
        }
    }
}