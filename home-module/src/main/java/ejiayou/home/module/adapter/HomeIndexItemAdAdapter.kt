package ejiayou.home.module.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import ejiayou.common.module.utils.ToastUtils
import ejiayou.home.module.R
import ejiayou.home.module.databinding.HomeIndexItemAdBinding
import ejiayou.home.module.databinding.HomeIndexItemBinding
import ejiayou.home.module.model.IndexItemAdDto
import ejiayou.home.module.model.IndexItemDto
import ejiayou.uikit.module.recyclerview.BaseBindRecyclerAdapter
import ejiayou.uikit.module.recyclerview.BaseRecyclerAdapter
import ejiayou.uikit.module.recyclerview.SpaceItemDecoration


/**
 * @author:
 * @created on: 2022/7/13 19:00
 * @description:
 */
class HomeIndexItemAdAdapter : BaseBindRecyclerAdapter<HomeIndexItemAdBinding, IndexItemAdDto>() {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.home_index_item_ad
    }

    override fun onBindingItem(binding: HomeIndexItemAdBinding, item: IndexItemAdDto, position: Int) {
        binding.homeIndexItemTvAdText.text = item.text
    }


}