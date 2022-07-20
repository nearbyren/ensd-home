package ejiayou.home.module.adapter

import ejiayou.common.module.exts.toColor
import ejiayou.home.module.R
import ejiayou.home.module.databinding.HomeFilterSuspendItemBinding
import ejiayou.home.module.model.FilterSuspendedItemDto
import ejiayou.uikit.module.recyclerview.BaseBindRecyclerAdapter


/**
 * @author:
 * @created on: 2022/7/13 19:00
 * @description:悬浮 筛选 油号 品牌
 */
class HomeFilterSuspenddItemAdapter : BaseBindRecyclerAdapter<HomeFilterSuspendItemBinding, FilterSuspendedItemDto>() {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.home_filter_suspend_item
    }

    override fun onBindingItem(binding: HomeFilterSuspendItemBinding, item: FilterSuspendedItemDto, position: Int) {
        if (item.check) {
            binding.homeFilterSuspendText.setBackgroundResource(R.drawable.home_filter_suspend_bg_orgrea)
            binding.homeFilterSuspendText.setTextColor(R.color.orange.toColor(binding.homeFilterSuspendText.context))
        } else {
            binding.homeFilterSuspendText.setBackgroundResource(R.drawable.home_filter_suspend_bg_white)
            binding.homeFilterSuspendText.setTextColor(R.color.background_color.toColor(binding.homeFilterSuspendText.context))
        }
        binding.homeFilterSuspendText.text = item.text

    }


}