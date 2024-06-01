package com.voiceeffects.supereffect.convertvideotomp3.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Build
import android.os.SystemClock
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMarginsRelative
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textfield.TextInputLayout
import com.voiceeffects.supereffect.convertvideotomp3.base.BaseFragment

fun View.hideKeyBoard() {
    val imm = context.applicationContext.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as? InputMethodManager
    imm?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyBoard() {
    val imm = this.context.applicationContext.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun View.hideKeyBoardWhenClickOutSide() {
    this.setOnClickListener {
        hideKeyBoard()
    }
}

@SuppressLint("ClickableViewAccessibility")
fun View.setHideKeyboardOnTouchOutside(
    excludeViewIds: List<Int> = mutableListOf(),
    nextFocus: View? = null,
    isClearFocus: Boolean = true,
    onHide: (() -> Unit)? = null
) {
    // Set up touch listener for non-text box views to hide keyboard.
    if (this !is EditText && excludeViewIds.contains(this.id).not()) {
        setOnTouchListener { _, _ ->
            this.hideKeyBoard()
            nextFocus?.requestFocus()
            onHide?.invoke()
            if (isClearFocus) {
                this.clearFocus()
            }
            false
        }
    }
    // If a layout containers, iterate over children and seed recursion.
    if (this is ViewGroup && this !is TextInputLayout) {
        forEach {
            it.setHideKeyboardOnTouchOutside(excludeViewIds, nextFocus, isClearFocus, onHide)
        }
    }
}

fun View.updateMargin(
    @Px start: Int? = null,
    @Px top: Int? = null,
    @Px end: Int? = null,
    @Px bottom: Int? = null
) {
    updateLayoutParams<ViewGroup.MarginLayoutParams> {
        // updateMargins(left = newMarginStart)
        updateMarginsRelative(
            start = start ?: marginStart,
            top = top ?: topMargin,
            end = end ?: marginEnd,
            bottom = bottom ?: bottomMargin
        )
    }
}

fun View.updatePadding(
    @Px start: Int? = null,
    @Px top: Int? = null,
    @Px end: Int? = null,
    @Px bottom: Int? = null
) {
    val startPx = start ?: this.paddingLeft
    val topPx = top ?: this.paddingTop
    val endPx = end ?: this.paddingRight
    val bottomPx = bottom ?: this.paddingBottom
    this.setPadding(startPx, topPx, endPx, bottomPx)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.showIf(isShow: Boolean) {
    visibility = if (isShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.invisibleIf(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

fun View.setOpacityEnable(isVisible: Boolean) {
    alpha = if (isVisible) 1f else 0.5f
    isEnabled = isVisible
}

var View.isEnableWithAlpha: Boolean
    set(value) {
        isEnabled = value
        alpha = if (value) 1F else 0.5F
    }
    get() = isEnabled

fun View.setOnViewVisible(onViewVisible: () -> Unit) {
    this.viewTreeObserver.addOnGlobalLayoutListener(
        object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                this@setOnViewVisible.viewTreeObserver.removeOnGlobalLayoutListener(this)
                onViewVisible()
            }
        }
    )
}

fun View.setEnableButton(isEnabledButton: Boolean, alPha: Float = 0.4f) {
    isEnabled = isEnabledButton
    alpha = if (isEnabledButton) 1f else alPha
}

fun View.setPaddingView(paddingTop: Int, paddingBottom: Int, paddingStart: Int, paddingEnd: Int) {
    this.setPadding(
        resources.getDimensionPixelOffset(paddingStart),
        resources.getDimensionPixelOffset(paddingTop),
        resources.getDimensionPixelSize(paddingEnd),
        resources.getDimensionPixelSize(paddingBottom)
    )
}

fun View.attachKeyboardListeners(
    navigationBarHeight: Int? = null,
    onKeyboardChange: ((Boolean, Int) -> Unit)? = null,
    isAttach: Boolean = true
) {
    var keyboardLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null
    var mIsShowKeyboard = false
    if (isAttach) {
        if (keyboardLayoutListener == null) {
            keyboardLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
                val r = Rect()
                val hasSoftKey = ViewConfiguration.get(context).hasPermanentMenuKey()
                this.getWindowVisibleDisplayFrame(r)
                val screenHeight = this.rootView.height
                val keypadHeight =
                    screenHeight - r.bottom - if (!hasSoftKey) navigationBarHeight ?: 0 else 0
                if (keypadHeight >= screenHeight * 0.15 && !mIsShowKeyboard) {
                    mIsShowKeyboard = true
                    onKeyboardChange?.invoke(mIsShowKeyboard, keypadHeight)
                } else if (keypadHeight < screenHeight * 0.15 && mIsShowKeyboard) {
                    mIsShowKeyboard = false
                    onKeyboardChange?.invoke(mIsShowKeyboard, keypadHeight)
                }
            }
        }
        this.viewTreeObserver.addOnGlobalLayoutListener(keyboardLayoutListener)
    } else {
        if (keyboardLayoutListener != null) {
            this.viewTreeObserver.removeOnGlobalLayoutListener(keyboardLayoutListener)
        }
    }
}

fun View.click(delayMs: Long = 1000L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < delayMs) return
            else action()
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun View.setOneTimeClickListener(delayMillis: Long = 2000, onAction: () -> Unit) {
    setOnClickListener {
        this.isEnabled = false
        onAction()
        postDelayed({ isEnabled = true }, delayMillis)
    }
}

//fun ShimmerFrameLayout.setShowShimmer(isShow: Boolean) {
//    this.isVisible = isShow
//    if (isShow) {
//        this.startShimmer()
//    } else {
//        this.stopShimmer()
//    }
//}

fun TextView.setTextPtpMessage(rickText: String?, onClickReadMore: () -> Unit) {
    if (rickText == null) return
    this.text = Html.fromHtml(rickText)
    this.movementMethod = LinkMovementMethod.getInstance()
    val spans = this.text as Spannable
    val urlSpans = spans.getSpans(0, spans.length, URLSpan::class.java)
    for (urlSpan in urlSpans) {
        val start = spans.getSpanStart(urlSpan)
        val end = spans.getSpanEnd(urlSpan)
        spans.removeSpan(urlSpan)
        spans.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                onClickReadMore.invoke()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.WHITE
                ds.isUnderlineText = true
                ds.typeface = Typeface.SANS_SERIF
            }
        }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    this.highlightColor = Color.TRANSPARENT
}

//@SuppressLint("ClickableViewAccessibility")
//fun View.clickLink(onClick: View.OnClickListener?) {
//    this.setOnTouchListener { view, motionEvent ->
//        when (motionEvent.action) {
//            MotionEvent.ACTION_DOWN -> {
//                (view as? TextView)?.apply {
//                    setTextColor(
//                        ContextCompat.getColor(
//                            context,
//                            R.color.colorPressHighlight
//                        )
//                    )
//                    setTextViewDrawableColor(this, R.color.colorPressHighlight)
//                }
//            }
//
//            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
//                (view as TextView)?.apply {
//                    setTextColor(
//                        ContextCompat.getColor(
//                            context,
//                            R.color.colorHighLight
//                        )
//                    )
//                    setTextViewDrawableColor(this, R.color.colorHighLight)
//                }
//            }
//        }
//        false
//    }
//    setOnClickListener(onClick)
//}

@SuppressLint("ClickableViewAccessibility")
fun View.clickLinkMore(
    colorPress: Int,
    colorHighLight: Int,
    onClick: View.OnClickListener?
) {
    this.setOnTouchListener { view, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                (view as? TextView)?.apply {
                    setTextColor(
                        ContextCompat.getColor(
                            context,
                            colorPress
                        )
                    )
                    setTextViewDrawableColor(this, colorPress)
                }
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                (view as? TextView)?.apply {
                    setTextColor(
                        ContextCompat.getColor(
                            context,
                            colorHighLight
                        )
                    )
                    setTextViewDrawableColor(this, colorHighLight)
                }
            }
        }
        false
    }
    setOnClickListener(onClick)
}

private fun setTextViewDrawableColor(textView: TextView, color: Int) {
    for (drawable in textView.compoundDrawables) {
        if (drawable != null) {
            drawable.colorFilter =
                PorterDuffColorFilter(
                    ContextCompat.getColor(textView.context, color),
                    PorterDuff.Mode.SRC_IN
                )
        }
    }
}

@SuppressLint("ClickableViewAccessibility")
fun View.clickLinkOpacity(onClick: View.OnClickListener?) {
    this.setOnTouchListener { view, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                (view as TextView).alpha = 0.5F
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                (view as TextView).alpha = 1F
            }
        }
        false
    }
    setOnClickListener(onClick)
}

fun Activity?.setResizeView(resize: Boolean) {
    if (resize.not()) {
        this?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    } else {
        this?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
}

/**
 * Update value for RangeSlider safely,
 * @param: passing null for unchanged field
 */
fun RangeSlider.updateValue(
    valueFrom: Float? = null,
    valueTo: Float? = null,
    values: List<Float>? = null
) {
    if (valueFrom != null) {
        this.valueFrom = valueFrom
    }
    if (valueTo != null) {
        this.valueTo = valueTo
    }
    //Correct the values to make sure its in range of valueFrom and valueTo
    //The values of Slider is immutable, therefore need to convert it to mutable to modify
    val mutableValues = (values ?: this.values).toMutableList()
    for (i in 0 until mutableValues.size) {
        if (mutableValues[i] < this.valueFrom) {
            mutableValues[i] = this.valueFrom
        }
        if (mutableValues[i] > this.valueTo) {
            mutableValues[i] = this.valueTo
        }
    }
    this.values = mutableValues.toList()
}

fun <T> BaseFragment.observer(liveData: LiveData<T>, onChange: (T?) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(onChange))
}

fun String.loadByHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(
            this,
            Html.FROM_HTML_MODE_COMPACT
        )
    } else {
        Html.fromHtml(this)
    }
}

//abstract class DebouncedOnClickListener(
//    private val delayBetweenClick: Long = DEFAULT_DEBOUNCE_INTERVAL
//) : View.OnClickListener {
//    private var lastClickTimestamp = ONE_NEGATIVE_LONG
//
//    abstract fun onDebouncedClick(view: View)
//
//    override fun onClick(v: View) {
//        val now = System.currentTimeMillis()
//
//        if (lastClickTimestamp == -1L || now >= lastClickTimestamp + delayBetweenClick) {
//            onDebouncedClick(v)
//        }
//
//        lastClickTimestamp = now
//    }
//}

//fun View.setOnSafeClick(
//    delayBetweenClick: Long = DEFAULT_DEBOUNCE_INTERVAL,
//    click: (view: View) -> Unit
//) {
//    setOnClickListener(object : DebouncedOnClickListener(delayBetweenClick) {
//        override fun onDebouncedClick(view: View) = click(view)
//    })
//}

/**
 * a farmer way to set button enabled state by changing its alpha (to avoid 2 or more xml state files for background, texts, ...)
 */
fun View.setEnabledByHand(isEnabled: Boolean) {
    alpha = if (isEnabled) {
        1f
    } else {
        0.5f
    }
    this.isEnabled = isEnabled
}

fun View.getLocationRectInWindow(): Rect {
    val viewLocation = IntArray(2)
    getLocationInWindow(viewLocation)
    return Rect(
        viewLocation[0],
        viewLocation[1],
        viewLocation[0] + measuredWidth,
        viewLocation[1] + measuredHeight
    )
}