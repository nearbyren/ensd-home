package ejiayou.home.module.view.banner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import ejiayou.home.module.view.BannerInfo
import java.util.ArrayList

/**
 * @author:
 * @created on: 2022/7/19 16:06
 * @description:
 */
abstract class BannerAdapter : PagerAdapter {
    var views: MutableList<View> = ArrayList()
    protected var mDataList: ArrayList<BannerInfo>? = null
    protected var mContext: Context
    protected var mListener: View.OnClickListener? = null

    constructor(mContext: Context) {
        this.mContext = mContext
    }

    constructor(mContext: Context, listener: View.OnClickListener?) {
        this.mContext = mContext
        mListener = listener
    }

    fun setOnItemClickListener(listener: View.OnClickListener?) {
        mListener = listener
    }

    fun setData(data: ArrayList<BannerInfo>) {
        mDataList = data
    }

    fun getItem(position: Int): BannerInfo? {
        return if (mDataList == null) {
            null
        } else {
            val pos = getPositionForIndicator(position)
            val size = mDataList!!.size
            if (size == 0) {
                null
            } else {
                if (pos < size) {
                    mDataList!![pos]
                } else {
                    null
                }
            }
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view: View? = null
        if (views.isEmpty()) {
            view = getView(LayoutInflater.from(container.context), null, position)
        } else {
            view = views.removeAt(0)
            getView(LayoutInflater.from(container.context), view, position)
        }
        if (mListener != null) {
            view!!.setOnClickListener(mListener)
        }
        container.addView(view)
        return view as Any
    }

//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        var view: View? = null
//        if (views.isEmpty()) {
//            view = getView(LayoutInflater.from(container.context), null, position)
//        } else {
//            view = views.removeAt(0)
//            getView(LayoutInflater.from(container.context), view, position)
//        }
//        if (mListener != null) {
//            view!!.setOnClickListener(mListener)
//        }
//        container.addView(view)
//        return view
//    }

    fun getPositionForIndicator(position: Int): Int {
        return if (null == mDataList || mDataList!!.size == 0) {
            0
        } else position % mDataList!!.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        views.add(`object`)
    }

    abstract fun getView(layoutInflater: LayoutInflater?, view: View?, position: Int): View?
    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun getCount(): Int {
        return if (mDataList == null) {
            0
        } else Int.MAX_VALUE
    }
}
