package ejiayou.home.module.adapter

import ejiayou.home.module.R
import ejiayou.home.module.databinding.HomeMenuItemBinding
import ejiayou.home.module.model.MenuItemDto
import ejiayou.uikit.module.recyclerview.BaseBindRecyclerAdapter


/**
 * @author:
 * @created on: 2022/7/13 19:00
 * @description:EPlus 开通会员
 */
class HomeMenuItemAdapter : BaseBindRecyclerAdapter<HomeMenuItemBinding, MenuItemDto>() {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.home_menu_item
    }

    override fun onBindingItem(binding: HomeMenuItemBinding, item: MenuItemDto, position: Int) {
        binding.homeIvMenuIcon.setBackgroundResource(item.icon)
        binding.homeTvMenuText.text = item.text

    }


}