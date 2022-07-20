package ejiayou.home.module.view.banner

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import ejiayou.home.module.R
import ejiayou.home.module.view.banner.AutoPlayer.Playable
import java.lang.Exception

/**
 * @author:
 * @created on: 2022/7/19 16:12
 * @description:
 */
class SliderBanner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    protected var mIdForViewPager = 0
    protected var mIdForIndicator = 0
    protected var mTimeInterval = 2000
    private var mViewPager: ViewPager? = null
    private var mBannerAdapter: BannerAdapter? = null
    private var mOnPageChangeListener: ViewPager.OnPageChangeListener? = null
    private var mPagerIndicator: PagerIndicator? = null
    private var mAutoPlayer: AutoPlayer? = null
    private var mViewPagerOnTouchListener: OnTouchListener? = null
    private val mGalleryPlayable: Playable = object : Playable {
        override fun playTo(to: Int) {
            mViewPager!!.setCurrentItem(to, true)
        }

        override fun playNext() {
            mViewPager!!.setCurrentItem(mViewPager!!.currentItem + 1, true)
        }

        override fun playPrevious() {
            mViewPager!!.setCurrentItem(mViewPager!!.currentItem - 1, true)
        }

        override val total: Int
            get() = mBannerAdapter!!.count
        override val current: Int
            get() = mViewPager!!.currentItem
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        try {
            val action = ev.action
            when (action) {
                MotionEvent.ACTION_DOWN -> if (mAutoPlayer != null) {
                    mAutoPlayer!!.pause()
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> if (mAutoPlayer != null) {
                    mAutoPlayer!!.resume()
                }
                else -> {
                }
            }
            if (mViewPagerOnTouchListener != null) {
                mViewPagerOnTouchListener!!.onTouch(this, ev)
            }
            return super.dispatchTouchEvent(ev)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun setViewPagerOnTouchListener(onTouchListener: OnTouchListener?) {
        mViewPagerOnTouchListener = onTouchListener
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mViewPager = findViewById<View>(mIdForViewPager) as ViewPager
        mPagerIndicator = findViewById<View>(mIdForIndicator) as PagerIndicator
        mViewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i2: Int) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener!!.onPageScrolled(i, v, i2)
                }
            }

            override fun onPageSelected(position: Int) {
                if (mPagerIndicator != null) {
                    mPagerIndicator!!.setSelected(mBannerAdapter!!.getPositionForIndicator(position))
                }
                mAutoPlayer!!.skipNext()
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener!!.onPageSelected(position)
                }
            }

            override fun onPageScrollStateChanged(i: Int) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener!!.onPageScrollStateChanged(i)
                }
            }
        })
        mAutoPlayer =
            AutoPlayer(mGalleryPlayable).setPlayRecycleMode(AutoPlayer.PlayRecycleMode.play_back)
        mAutoPlayer!!.setTimeInterval(mTimeInterval)
    }

    fun setTimeInterval(interval: Int) {
        mAutoPlayer!!.setTimeInterval(interval)
    }

    fun setAdapter(adapter: BannerAdapter?) {
        mBannerAdapter = adapter
        mViewPager!!.adapter = adapter
    }

    fun beginPlay() {
        if (mAutoPlayer != null) mAutoPlayer!!.play()
    }

    fun pause() {
        if (mAutoPlayer != null) mAutoPlayer!!.pause()
    }

    fun resume() {
        if (mAutoPlayer != null) mAutoPlayer!!.resume()
    }

    fun stop() {
        if (mAutoPlayer != null) mAutoPlayer!!.stop()
    }

    fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener?) {
        mOnPageChangeListener = listener
    }

    fun setDotNum(num: Int) {
        if (mPagerIndicator != null) {
            mPagerIndicator!!.setNum(num)
        }
    }

    fun setDotNumSize(num: Int) {
        if (mPagerIndicator != null) {
            mPagerIndicator!!.setNumber(num)
        }
    }

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.SliderBanner, 0, 0)
        if (arr != null) {
            if (arr.hasValue(R.styleable.SliderBanner_slider_banner_pager)) {
                mIdForViewPager = arr.getResourceId(R.styleable.SliderBanner_slider_banner_pager, 0)
            }
            if (arr.hasValue(R.styleable.SliderBanner_slider_banner_indicator)) {
                mIdForIndicator =
                    arr.getResourceId(R.styleable.SliderBanner_slider_banner_indicator, 0)
            }
            mTimeInterval =
                arr.getInt(R.styleable.SliderBanner_slider_banner_time_interval, mTimeInterval)
            arr.recycle()
        }
    }
}
