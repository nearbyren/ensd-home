package ejiayou.home.module.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import ejiayou.home.module.R
import ejiayou.home.module.view.BannerInfo
import ejiayou.home.module.view.banner.BannerAdapter

/**
 * @author:
 * @created on: 2022/7/19 16:30
 * @description:
 */
class IndexBannerAdapter(mContext: Context?) : BannerAdapter(mContext!!) {


    override fun getView(layoutInflater: LayoutInflater?, view: View?, position: Int): View? {
        var convertView: View? = null
        if (view == null) {
            if (layoutInflater != null) {
                convertView = layoutInflater.inflate(R.layout.home_item_banner_image, null)
            }
        } else {
            convertView = view
        }
        val item: BannerInfo = getItem(position)!!
        val imageView = convertView!!.findViewById<View>(R.id.bg_imageview) as ImageView
        if (item != null) {
            Glide.with(mContext)
                    .load(item.url)
                    .into(imageView)
        }
        convertView.tag = position
        return convertView
    }
}
