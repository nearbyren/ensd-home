package ejiayou.home.module.view.banner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import ejiayou.home.module.R

/**
 * @author:
 * @created on: 2022/7/19 16:12
 * @description:
 */
class SecondDotView : RelativeLayout, PagerIndicator {
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
    private val mOnDotClickHandler: OnDotClickHandler? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context
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
        for (i in 0 until number) {
            val view: View = LayoutInflater.from(mContext).inflate(R.layout.home_dot_number_view, null)
            addView(view)
        }
        val v = getChildAt(0) as View
        if (v != null) {
            val tv = v.findViewById<TextView>(R.id.tv_number)
            v.setBackgroundResource(R.drawable.home_dot_number_view_bg)
            tv.text = "1/$mTotal"
        }
    }

    override fun setNum(num: Int) {}
    override fun getTotal(): Int {
        return mTotal
    }

    override fun getCurrentIndex(): Int {
        return mCurrent
    }

    override fun setSelected(index: Int) {
        if (index >= childCount || index < 0 || mCurrent == index) {
            return
        }
        //数字处理
        if (mCurrent < childCount && mCurrent >= 0) {
            val v = getChildAt(mCurrent) as View
            if (v != null) {
                val tv = v.findViewById<TextView>(R.id.tv_number)
                v.background = null
                tv.text = ""
            }
        }
        val v = getChildAt(index) as View
        if (v != null) {
            v.setBackgroundResource(R.drawable.home_dot_number_view_bg)
            val tv = v.findViewById<TextView>(R.id.tv_number)
            tv.text = (index + 1).toString() + "/" + mTotal
            mCurrent = index
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
