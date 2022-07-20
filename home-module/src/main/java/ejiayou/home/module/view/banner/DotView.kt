package ejiayou.home.module.view.banner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import ejiayou.home.module.R

/**
 * @author:
 * @created on: 2022/7/19 16:10
 * @description:
 */
class DotView : LinearLayout, PagerIndicator {
    interface OnDotClickHandler {
        fun onDotClick(index: Int)
    }

    var mContext: Context? = null
    private var mLittleDotSize = -2
    private var mDotSpan = 36
    private var mDotRadius = 6f
    private var isNumberSize = false
    private var mCurrent = 0
    private var mTotal = 0
    private var mSelectedColor = -0xc88412
    private var mUnSelectedColor = -0x3a3125
    private var mOnDotClickHandler: OnDotClickHandler? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context
        gravity = Gravity.CENTER_HORIZONTAL
        val arr = context.obtainStyledAttributes(attrs, R.styleable.DotView, 0, 0)
        if (arr != null) {
            if (arr.hasValue(R.styleable.DotView_dot_radius)) {
                mDotRadius = arr.getDimension(R.styleable.DotView_dot_radius, mDotRadius)
            }
            if (arr.hasValue(R.styleable.DotView_dot_span)) {
                mDotSpan =
                    arr.getDimension(R.styleable.DotView_dot_span, mDotSpan.toFloat()).toInt()
            }
            mSelectedColor = arr.getColor(R.styleable.DotView_dot_selected_color, mSelectedColor)
            mUnSelectedColor =
                arr.getColor(R.styleable.DotView_dot_unselected_color, mUnSelectedColor)
            arr.recycle()
        }
        mLittleDotSize = (mDotSpan / 2 + mDotRadius * 2).toInt()
    }

    override fun setNumber(number: Int) {
        if (number < 0) return
        isNumberSize = true
        mCurrent = 0
        mTotal = number
        removeAllViews()
        orientation = HORIZONTAL
        for (i in 0 until number) {
            val view: View = LayoutInflater.from(mContext).inflate(R.layout.home_dot_number_view, null)
            view.isClickable = true
            view.setOnClickListener(mDotClickHandler)
            addView(view)
        }
    }

    override fun setNum(num: Int) {
        if (num < 0) return
        mCurrent = 0
        mTotal = num
        removeAllViews()
        orientation = HORIZONTAL
        for (i in 0 until num) {
            val dot: LittleDot = LittleDot(context, i)
            if (i == 0) {
                dot.setColor(mSelectedColor)
            } else {
                dot.setColor(mUnSelectedColor)
            }
            dot.layoutParams = LayoutParams(mLittleDotSize, mDotRadius.toInt() * 2, 1f)
            dot.isClickable = true
            dot.setOnClickListener(mDotClickHandler)
            addView(dot)
        }
    }

    override fun getTotal(): Int {
        return mTotal
    }

    override fun getCurrentIndex(): Int {
        return mCurrent
    }

    fun setOnDotClickHandler(handler: OnDotClickHandler?) {
        mOnDotClickHandler = handler
    }

    private val mDotClickHandler = OnClickListener { v ->
        if (v is LittleDot && null != mOnDotClickHandler) {
            mOnDotClickHandler!!.onDotClick(v.index)
        }
    }

    override fun setSelected(index: Int) {
        if (index >= childCount || index < 0 || mCurrent == index) {
            return
        }
        //数字处理
        if (isNumberSize) {
            if (mCurrent < childCount && mCurrent >= 0) {
                ((getChildAt(mCurrent) as View).findViewById<View>(R.id.tv_number) as TextView).text =
                    "$mCurrent/$mTotal"
            }
        } else {
            if (mCurrent < childCount && mCurrent >= 0) {
                (getChildAt(mCurrent) as LittleDot).setColor(mUnSelectedColor)
            }
            (getChildAt(index) as LittleDot).setColor(mSelectedColor)
        }
        mCurrent = index
    }

    private inner class LittleDot(context: Context?, index: Int) : View(context) {
        private var mColor = 0
        private val mPaint: Paint
        val index: Int

        fun setColor(color: Int) {
            if (color == mColor) return
            mColor = color
            invalidate()
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            mPaint.color = mColor
            canvas.drawCircle((mLittleDotSize / 2).toFloat(), mDotRadius, mDotRadius * 3 / 4, mPaint)
        }

        init {
            mPaint = Paint()
            mPaint.isAntiAlias = true
            this.index = index
        }
    }

    fun setSelectedColor(color: Int) {
        if (mSelectedColor != color) {
            mSelectedColor = color
            invalidate()
        }
    }

    fun setColor(selectedColor: Int, unSelectedColor: Int) {
        if (mSelectedColor != selectedColor || mUnSelectedColor != unSelectedColor) {
            mSelectedColor = selectedColor
            mUnSelectedColor = unSelectedColor
            invalidate()
        }
    }

    fun setUnSelectedColor(color: Int) {
        if (mUnSelectedColor != color) {
            mSelectedColor = color
            invalidate()
        }
    }
}
