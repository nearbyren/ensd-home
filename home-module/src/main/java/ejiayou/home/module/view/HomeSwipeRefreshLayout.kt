package ejiayou.home.module.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState


/**
 * @ClassName: MySwipeRefreshLayout
 * @Description: java类作用描述
 */
class HomeSwipeRefreshLayout(context: Context, attrs: AttributeSet?) : SmartRefreshLayout(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return if (mState == RefreshState.Refreshing) {
            true
        } else {
            super.dispatchTouchEvent(ev)
        }
    }
}