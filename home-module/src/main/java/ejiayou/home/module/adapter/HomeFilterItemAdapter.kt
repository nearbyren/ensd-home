package ejiayou.home.module.adapter

import ejiayou.home.module.R
import ejiayou.home.module.databinding.HomeFilterItemBinding
import ejiayou.home.module.databinding.HomeMenuItemBinding
import ejiayou.home.module.model.FilterItemDto
import ejiayou.home.module.model.MenuItemDto
import ejiayou.uikit.module.recyclerview.BaseBindRecyclerAdapter


/**
 * @author:
 * @created on: 2022/7/13 19:00
 * @description:EPlus 开通会员
 */
class HomeFilterItemAdapter : BaseBindRecyclerAdapter<HomeFilterItemBinding, FilterItemDto>() {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.home_filter_item
    }

    override fun onBindingItem(binding: HomeFilterItemBinding, item: FilterItemDto, position: Int) {
        binding.homeIvFilterIcon.setBackgroundResource(item.icon)
        binding.homeTvFilterText.text = item.text
    }


}