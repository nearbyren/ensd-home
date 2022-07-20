package ejiayou.home.module.adapter

import androidx.core.view.isVisible
import ejiayou.common.module.exts.toColor
import ejiayou.home.module.R
import ejiayou.home.module.databinding.HomeActivityItemBinding
import ejiayou.home.module.databinding.HomeFilterSuspendItemBinding
import ejiayou.home.module.model.ActivityItemDto
import ejiayou.home.module.model.FilterSuspendedItemDto
import ejiayou.uikit.module.recyclerview.BaseBindRecyclerAdapter


/**
 * @author:
 * @created on: 2022/7/13 19:00
 * @description:悬浮 筛选 油号 品牌
 */
class HomeActivityItemAdapter : BaseBindRecyclerAdapter<HomeActivityItemBinding, ActivityItemDto>() {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.home_activity_item
    }

    override fun onBindingItem(binding: HomeActivityItemBinding, item: ActivityItemDto, position: Int) {
        if (item.icon > 0) {
            binding.homeActivityIvIcon.isVisible = true
            binding.homeActivityText.isVisible = false
        } else {
            binding.homeActivityIvIcon.isVisible = false
            binding.homeActivityText.isVisible = true
        }
        binding.homeActivityText.text = item.text
    }


}