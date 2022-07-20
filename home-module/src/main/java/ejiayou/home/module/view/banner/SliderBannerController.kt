package ejiayou.home.module.view.banner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import ejiayou.home.module.R
import ejiayou.home.module.view.BannerInfo
import java.util.ArrayList

/**
 * @author:
 * @created on: 2022/7/19 16:13
 * @description:
 */
class SliderBannerController {
    private var mContenxt: Context
    private var mSliderBanner: SliderBanner
    private var mBannerAdapter: BannerAdapter
    private var mOnBannerItemClickListener: OnBannerItemClickListener? = null
    fun setOnBannerItemClickListener(onBannerItemClickListener: OnBannerItemClickListener?) {
        mOnBannerItemClickListener = onBannerItemClickListener
    }

    interface OnBannerItemClickListener {
        fun OnItemClick(view: View?, postion: Int)
    }

    private val mClickItemListener = View.OnClickListener { view ->
        if (mOnBannerItemClickListener != null) {
            mOnBannerItemClickListener!!.OnItemClick(view, view.tag as Int)
        }
    }

    constructor(context: Context, sliderBanner: SliderBanner) {
        mContenxt = context
        mSliderBanner = sliderBanner
        mBannerAdapter = InnerAdapter(context, mClickItemListener)
        sliderBanner.setAdapter(mBannerAdapter)
    }

    constructor(context: Context, sliderBanner: SliderBanner, adapter: BannerAdapter) {
        mContenxt = context
        mSliderBanner = sliderBanner
        mBannerAdapter = adapter
        mBannerAdapter.setOnItemClickListener(mClickItemListener)
        sliderBanner.setAdapter(mBannerAdapter)
    }

    fun play(list: ArrayList<BannerInfo>) {
        mBannerAdapter.setData(list)
        mBannerAdapter.notifyDataSetChanged()
        mSliderBanner.setDotNum(list.size)
        mSliderBanner.beginPlay()
    }

    fun playNumSize(list: ArrayList<BannerInfo>) {
        mBannerAdapter.setData(list)
        mBannerAdapter.notifyDataSetChanged()
        mSliderBanner.setDotNumSize(list.size)
        mSliderBanner.beginPlay()
    }

    private inner class InnerAdapter(mContext: Context?, listener: View.OnClickListener?) : BannerAdapter(mContext!!, listener) {
        override fun getView(layoutInflater: LayoutInflater?, view: View?, position: Int): View? {
            var convertView: View? = null
            if (view == null) {
                if (layoutInflater != null) {
                    convertView = layoutInflater.inflate(R.layout.home_item_banner_image, null)
                }
            } else {
                convertView = view
            }
            val item = getItem(position)
            val imageView = convertView!!.findViewById<View>(R.id.bg_imageview) as ImageView
            imageView.adjustViewBounds = false
            //            if (item != null)
//                Glide.with(mContenxt.getApplicationContext()).load(item.url).centerCrop().placeholder(R.drawable.guanggao2).into(imageView);
            convertView.tag = position
            return convertView
        }
    }
}
