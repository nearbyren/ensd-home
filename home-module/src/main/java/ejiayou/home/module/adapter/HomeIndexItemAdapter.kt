package ejiayou.home.module.adapter

import android.content.Context
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ejiayou.common.module.utils.ToastUtils
import ejiayou.home.module.R
import ejiayou.home.module.databinding.HomeIndexItemBinding
import ejiayou.home.module.model.IndexItemAdDto
import ejiayou.home.module.model.IndexItemDto
import ejiayou.uikit.module.recyclerview.BaseBindRecyclerAdapter
import ejiayou.uikit.module.recyclerview.BaseRecyclerAdapter
import ejiayou.uikit.module.recyclerview.SpaceItemDecoration
import java.util.ArrayList


/**
 * @author:
 * @created on: 2022/7/13 19:00
 * @description:首页 item
 */
class HomeIndexItemAdapter(var context: Context) : BaseBindRecyclerAdapter<HomeIndexItemBinding, IndexItemDto>() {
    private val adAdapter by lazy { HomeIndexItemAdAdapter() }
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.home_index_item
    }

    var indexItemDtos =
        arrayListOf(IndexItemAdDto(text = "农行特惠"), IndexItemAdDto(text = "农行特惠"), IndexItemAdDto(text = "农行特惠"), IndexItemAdDto(text = "农行特惠"))

    override fun onBindingItem(binding: HomeIndexItemBinding, item: IndexItemDto, position: Int) {
        adAdapter.setItems(indexItemDtos)
        binding.homeIndexRecycleAd.adapter = adAdapter
        if (position % 2 == 0) {
            binding.homeRlAd.isVisible = false
            binding.homeTvAdDiscount.isVisible = false
        } else {
            binding.homeRlAd.isVisible = true
            binding.homeTvAdDiscount.isVisible = true
        }
        binding.homeIndexRecycleAd.layoutManager = GridLayoutManager(context, 4)
        val spaceItemDecoration = SpaceItemDecoration(space = 10)
        binding.homeIndexRecycleAd.addItemDecoration(spaceItemDecoration)
        binding.homeIndexRecycleAd.isNestedScrollingEnabled = false
        binding.homeIndexRecycleAd.setHasFixedSize(true)
        adAdapter.setOnItemClickListener(listener = object : BaseRecyclerAdapter.OnItemClickListener<IndexItemAdDto> {
            override fun onItemClick(holder: Any, item: IndexItemAdDto, position: Int) {
                item.text?.let {

                }
            }
        })
    }


}