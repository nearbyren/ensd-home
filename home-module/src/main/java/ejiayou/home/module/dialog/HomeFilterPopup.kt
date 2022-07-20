package ejiayou.home.module.dialog

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ejiayou.common.module.utils.ToastUtils
import ejiayou.home.module.R
import ejiayou.home.module.adapter.HomeFilterSuspenddItemAdapter
import ejiayou.home.module.adapter.HomeMenuItemAdapter
import ejiayou.home.module.databinding.HomeFilterBinding
import ejiayou.home.module.databinding.HomeFilterPopupsBinding
import ejiayou.home.module.model.FilterSuspendedItemDto
import ejiayou.home.module.model.MenuItemDto
import ejiayou.uikit.module.popupwindow.BaseBindPopupWindow
import ejiayou.uikit.module.popupwindow.BasePopupWindow
import ejiayou.uikit.module.recyclerview.BaseRecyclerAdapter
import ejiayou.uikit.module.recyclerview.SpaceItemDecoration
import java.util.ArrayList
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.core.view.isVisible
import ejiayou.uikit.module.dpToPx
import kotlin.random.Random


/**
 * @author:
 * @created on: 2022/7/20 14:05
 * @description:
 */
class HomeFilterPopup(context: Context) : BaseBindPopupWindow<HomeFilterPopupsBinding>(context) {


    override fun getLayoutId(): Int {
        return R.layout.home_filter_popups
    }


    var filterSuspendedItemDtos = ArrayList<FilterSuspendedItemDto>()


    fun refresh(datas: ArrayList<FilterSuspendedItemDto>) {
        filterSuspendedItemDtos = datas
        homeFilterSuspenddItemAdapter?.let {
            it.setItems(filterSuspendedItemDtos)
            it.notifyDataSetChanged()
        }
    }

    var homeFilterSuspenddItemAdapter: HomeFilterSuspenddItemAdapter? = null

    override fun initContentView() {
        super.initContentView()
        Log.d("TTT", "initContentView")
        width = ViewGroup.LayoutParams.MATCH_PARENT
        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        homeFilterSuspenddItemAdapter = HomeFilterSuspenddItemAdapter()
        binding.homeSuspendedFilterRecycle.adapter = homeFilterSuspenddItemAdapter
        binding.homeSuspendedFilterRecycle.layoutManager = GridLayoutManager(context, 4)
        binding.homeSuspendedFilterRecycle.setHasFixedSize(true)
        homeFilterSuspenddItemAdapter?.let {
            it.setOnItemClickListener(listener = object : BaseRecyclerAdapter.OnItemClickListener<FilterSuspendedItemDto> {
                override fun onItemClick(holder: Any, item: FilterSuspendedItemDto, position: Int) {
                    var check = item.check
                    for (c in filterSuspendedItemDtos) {
                        c.check = false
                    }
                    filterSuspendedItemDtos[position].check = !check
                    homeFilterSuspenddItemAdapter!!.notifyDataSetChanged()
                    item.text?.let {
                        ToastUtils.showToast(context, it)
                    }
                }
            })
        }

        binding.homeSuspendedFilterBrand.isVisible = Random.nextInt(2) == 0
        binding.homeRootFilter.setOnClickListener {
            filterDismiss()
        }
    }

    fun filterDismiss() {
        this.dismiss()
    }

    /***
     * 处理android7.0以上不显示
     */
    override fun showAsDropDown(anchor: View) {
        if (Build.VERSION.SDK_INT >= 24) {
            val rect = Rect()
            anchor.getGlobalVisibleRect(rect)
            val h: Int = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom
            Log.d("TTT", "h = $h bottom = ${rect.bottom} heightPixels = ${anchor.getResources().getDisplayMetrics().heightPixels}")
            height = h
        }
        super.showAsDropDown(anchor)
    }
}