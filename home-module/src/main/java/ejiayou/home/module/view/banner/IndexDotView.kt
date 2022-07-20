package ejiayou.home.module.view.banner

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import ejiayou.home.module.R

/**
 * @author:
 * @created on: 2022/7/19 16:11
 * @description:
 */
class IndexDotView : LinearLayout, PagerIndicator {
    private var mLittleDotSize = -2 //圆点选中器宽度 + 加间隔
    private var mLittleDotSelctedSize = -2 //长条选中器宽度 + 加间隔
    private var mDotSpan = 26 //间隔宽度
    private var mDotRadius = 1f //宽度
    private var mDotSelWidth = 45f //选中长条宽度
    private var mCurrent = 0
    private var mTotal = 0
    private var mSelectedColor = -0x1
    private var mUnSelectedColor = -0x4DFFFFFF

    constructor(context: Context?) : super(context) {}


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        gravity = Gravity.CENTER_HORIZONTAL
        val arr = context.obtainStyledAttributes(attrs, R.styleable.DotView, 0, 0)
        if (arr != null) {
            if (arr.hasValue(R.styleable.DotView_dot_radius)) {
                mDotRadius = arr.getDimension(R.styleable.DotView_dot_radius, mDotRadius)
            }
            if (arr.hasValue(R.styleable.DotView_dot_selected_width)) {
                mDotSelWidth = arr.getDimension(R.styleable.DotView_dot_selected_width, mDotRadius)
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
        mLittleDotSize = (mDotSpan + mDotRadius * 2).toInt()
        mLittleDotSelctedSize = (mDotSpan + mDotSelWidth).toInt()
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
                    dot.layoutParams = LayoutParams(mLittleDotSelctedSize, mDotRadius.toInt() * 2)
                } else {
                    dot.setColor(mUnSelectedColor)
                    dot.layoutParams = LayoutParams(mLittleDotSelctedSize, mDotRadius.toInt() * 2)
                }
                addView(dot)
            }
    }

    override fun getTotal(): Int {
        return mTotal
    }

    override fun getCurrentIndex(): Int {
        return mCurrent
    }

    override fun setNumber(number: Int) {}
    override fun setSelected(index: Int) {
        if (index >= childCount || index < 0 || mCurrent == index) {
            return
        }
        for (i in 0 until mTotal) {
            val dot = getChildAt(i) as LittleDot
            if (i == index) {
                dot.layoutParams = LayoutParams(mLittleDotSelctedSize, mDotRadius.toInt() * 3)
                dot.setColor(mSelectedColor)
            } else {
                dot.layoutParams = LayoutParams(mLittleDotSelctedSize, mDotRadius.toInt() * 3)
                dot.setColor(mUnSelectedColor)
            }
        }
        mCurrent = index
    }

    private inner class LittleDot(context: Context?, index: Int) : View(context) {
        private var mColor = 0
        private val mPaint: Paint
        val index: Int
        private val rectF: RectF
        fun setColor(color: Int) {
            if (color == mColor) return
            mColor = color
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            mPaint.color = mColor
            if (index == mCurrent) {
                canvas.drawRoundRect(rectF, mDotRadius, mDotRadius, mPaint) //第二个参数是x半径，第三个参数是y半径
            } else {
                canvas.drawRoundRect(rectF, mDotRadius, mDotRadius, mPaint)
            }
        }

        init {
            mPaint = Paint()
            mPaint.isAntiAlias = true
            this.index = index
            rectF = RectF(
                mLittleDotSelctedSize / 2 - mDotSelWidth / 2,
                0f,
                mLittleDotSelctedSize / 2 + mDotSelWidth / 2,
                mDotRadius * 2)
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
