package ejiayou.home.module.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @author:
 * @created on: 2022/7/21 11:08
 * @description:
 */
class MainPager : ViewPager {


    //取消滚动
    private var isCanScroll = false
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}

    fun setScanScroll(isCanScroll: Boolean) {
        this.isCanScroll = isCanScroll
    }

    //去除页面切换时的滑动翻页效果
    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item, false)
    }

    override fun onTouchEvent(arg0: MotionEvent?): Boolean {
        // TODO Auto-generated method stub
        return if (isCanScroll) {
            super.onTouchEvent(arg0)
        } else {
            false
        }
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent?): Boolean {
        // TODO Auto-generated method stub
        return if (isCanScroll) {
            super.onInterceptTouchEvent(arg0)
        } else {
            false
        }
    }
}
