package ejiayou.home.module

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.listener.OnMultiListener
import ejiayou.common.module.base.BaseAppBVMActivity
import ejiayou.common.module.utils.ToastUtils
import ejiayou.home.module.adapter.*
import ejiayou.home.module.view.BannerInfo
import ejiayou.home.module.view.MyRefreshFooter
import ejiayou.home.module.view.MyRefreshHeader
import ejiayou.home.module.view.banner.SliderBannerController
import ejiayou.uikit.module.recyclerview.BaseRecyclerAdapter
import ejiayou.uikit.module.recyclerview.SpaceItemDecoration
import java.lang.Exception
import java.util.ArrayList


import ejiayou.home.module.databinding.HomeIndexActivityBinding
import ejiayou.home.module.dialog.HomeFilterPopup
import ejiayou.home.module.model.*


/**
 * @author: lr
 * @created on: 2022/7/16 4:42 下午
 * @description:
 */
class IndexActivity : BaseAppBVMActivity<HomeIndexActivityBinding, IndeMainModel>(), OnMultiListener {

    override fun layoutRes(): Int {
        return R.layout.home_index_activity
    }

    private val menuAdapter by lazy { HomeMenuItemAdapter() }
    private val filterAdapter by lazy { HomeFilterItemAdapter() }
    private val activityAdapter by lazy { HomeActivityItemAdapter() }
    private val indexAdapter by lazy { HomeIndexItemAdapter(applicationContext) }
    var menuItems = ArrayList<MenuItemDto>()
    var filterItems = ArrayList<FilterItemDto>()
    var activityItems = ArrayList<ActivityItemDto>()
    var indexItems = ArrayList<IndexItemDto>()
    var bannerInfos = ArrayList<BannerInfo>()
    var filterPopup: HomeFilterPopup? = null

    var myRefreshFooter: MyRefreshFooter? = null
    var scrollPercent: Float = 0.0F
    var positionFilter: Int = 0

    fun initBanner() {
        var layoutParams = binding.homeBanner.homeSliderBanner.layoutParams
        val radio = 103 / 375.0f
        var he = (1980 * radio)
        layoutParams.height = he.toInt()
        binding.homeBanner.homeSliderBanner.layoutParams = layoutParams
        val controller =
            SliderBannerController(MainActivity2@ this, binding.homeBanner.homeSliderBanner, IndexBannerAdapter(MainActivity2@ this))
        controller.setOnBannerItemClickListener(object : SliderBannerController.OnBannerItemClickListener {
            override fun OnItemClick(view: View?, postion: Int) {

            }
        })
        bannerInfos.add(BannerInfo(url = "https://img.ejiayou.com/upload/2022/2/540e264d-6e3c-42c8-a827-fc303977a66a-1644806671498.jpg"))
        bannerInfos.add(BannerInfo(url = "https://img.ejiayou.com/upload/2022/4/0ef6e938-5dd4-411b-bcf2-2647aec5b970-1649750972617.jpg"))
        bannerInfos.add(BannerInfo(url = "https://img.ejiayou.com/upload/2022/6/05dfb5b6-b5f2-412c-976d-9c8fa802ea0d-1654162631084.jpg"))
        bannerInfos.add(BannerInfo(url = "https://img.ejiayou.com/upload/2022/7/f6003ecd-730a-4c95-9705-478a45e20ea8-1657276099174.jpg"))
        controller.play(bannerInfos)
    }

    fun initMenu() {
        menuItems.add(MenuItemDto(R.drawable.home_index1, 1, "车主商城"))
        menuItems.add(MenuItemDto(R.drawable.home_index2, 1, "认证有礼"))
        menuItems.add(MenuItemDto(R.drawable.home_index3, 1, "洗车特惠"))
        menuItems.add(MenuItemDto(R.drawable.home_index4, 1, "邀请有礼"))
        menuAdapter.setItems(menuItems)
        binding.homeBanner.homeMenuRecycle.adapter = menuAdapter
        binding.homeBanner.homeMenuRecycle.layoutManager = GridLayoutManager(this, 4)
        val spaceItemDecoration = SpaceItemDecoration(0, 10, 35)
        binding.homeBanner.homeMenuRecycle.addItemDecoration(spaceItemDecoration)
        binding.homeBanner.homeMenuRecycle.setHasFixedSize(true)
        menuAdapter.setOnItemClickListener(listener = object : BaseRecyclerAdapter.OnItemClickListener<MenuItemDto> {
            override fun onItemClick(holder: Any, item: MenuItemDto, position: Int) {
                item.text?.let {
                    ToastUtils.showToast(applicationContext, it)
                }
            }
        })

    }

    fun initFilter() {
        filterPopup()
        filterItems.add(FilterItemDto(icon = R.drawable.home_filter_composite_sort_select_icon, type = 1, sort = false, text = "距离排序"))
        filterItems.add(FilterItemDto(icon = R.drawable.home_filter_price_sort_select_icon, type = 1, sort = false, text = "价格排序"))
        filterItems.add(FilterItemDto(icon = R.drawable.home_filter_down_icon, type = 2, text = "油号92#"))
        filterItems.add(FilterItemDto(icon = R.drawable.home_filter_down_icon, type = 3, text = "品牌筛选"))
        filterAdapter.setItems(filterItems)
        binding.homeFilterRecycle.adapter = filterAdapter
        binding.homeFilterRecycle.layoutManager = GridLayoutManager(this, 4)
        val spaceItemDecoration = SpaceItemDecoration(0, 10, 35)
        binding.homeFilterRecycle.addItemDecoration(spaceItemDecoration)
        binding.homeFilterRecycle.setHasFixedSize(true)
        filterAdapter.setOnItemClickListener(listener = object : BaseRecyclerAdapter.OnItemClickListener<FilterItemDto> {
            override fun onItemClick(holder: Any, item: FilterItemDto, position: Int) {
                item.text?.let {
                    ToastUtils.showToast(applicationContext, it)
                }
                if (item.type < 1) return
                filterPopup?.let {
                    if (item.type == positionFilter && it.isShowing) {
                        it.dismiss()
                        return
                    } else {
                        it.dismiss()
                    }
                    //标记点击记录
                    positionFilter = item.type
                    it.showAsDropDown(binding.homeFilterLine)
                }
            }
        })
    }

    fun initActivity() {
        activityItems.add(ActivityItemDto(icon = R.drawable.home_activity_icon))
        activityItems.add(ActivityItemDto(text = "银行满减"))
        activityItems.add(ActivityItemDto(text = "加油送礼"))
        activityAdapter.setItems(activityItems)
        binding.homeActivityRecycle.adapter = activityAdapter
        binding.homeActivityRecycle.layoutManager = GridLayoutManager(this, 4)
        val spaceItemDecoration = SpaceItemDecoration(0, 10, 10)
        binding.homeActivityRecycle.addItemDecoration(spaceItemDecoration)
        binding.homeActivityRecycle.setHasFixedSize(true)
        activityAdapter.setOnItemClickListener(listener = object : BaseRecyclerAdapter.OnItemClickListener<ActivityItemDto> {
            override fun onItemClick(holder: Any, item: ActivityItemDto, position: Int) {
                item.text?.let {
                    ToastUtils.showToast(applicationContext, it)

                }
            }
        })
    }

    fun initIndex() {
        indexItems.add(IndexItemDto())
        indexItems.add(IndexItemDto())
        indexItems.add(IndexItemDto())
        indexItems.add(IndexItemDto())
        indexItems.add(IndexItemDto())
        indexItems.add(IndexItemDto())
        indexItems.add(IndexItemDto())
        indexItems.add(IndexItemDto())
        indexItems.add(IndexItemDto())
        indexItems.add(IndexItemDto())
        indexItems.add(IndexItemDto())
        indexAdapter.setItems(indexItems)
        binding.homeIndexRecycle.adapter = indexAdapter
        binding.homeIndexRecycle.layoutManager = LinearLayoutManager(this)
        val spaceItemDecoration = SpaceItemDecoration(space = 10)
        binding.homeIndexRecycle.addItemDecoration(spaceItemDecoration)
        binding.homeIndexRecycle.setHasFixedSize(true)
        indexAdapter.setOnItemClickListener(listener = object : BaseRecyclerAdapter.OnItemClickListener<IndexItemDto> {
            override fun onItemClick(holder: Any, item: IndexItemDto, position: Int) {
                item.text?.let {
                    ToastUtils.showToast(applicationContext, it)
                }
            }
        })

    }

    fun filterPopup() {
        filterPopup = HomeFilterPopup(IndexActivity@ this)
        var filterSuspendedItemDtos = ArrayList<FilterSuspendedItemDto>()
        filterSuspendedItemDtos.add(FilterSuspendedItemDto(text = "92号", check = true))
        filterSuspendedItemDtos.add(FilterSuspendedItemDto(text = "95号"))
        filterSuspendedItemDtos.add(FilterSuspendedItemDto(text = "98号"))
        filterSuspendedItemDtos.add(FilterSuspendedItemDto(text = "0号"))
        filterPopup!!.refresh(filterSuspendedItemDtos)

    }

    //处理滑动关闭筛选框
    fun scrollFilterClose(mPercent: Float = 0.0F) {
        filterPopup?.let {
            if (scrollPercent != mPercent) {
                filterPopup?.let {
                    if (it.isShowing) {
                        it.dismiss()
                    }
                }
            }
        }

    }

    override fun initialize(savedInstanceState: Bundle?) {

        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                try {
                    //AppBarLayout竖直方向偏移距离px
                    val absVerticalOffset = Math.abs(verticalOffset)
                    //AppBarLayout总的距离px
                    val totalScrollRange = appBarLayout!!.totalScrollRange
                    //滑动百分比
                    var mPercent = absVerticalOffset.toFloat() / totalScrollRange
                    scrollFilterClose(mPercent)
                    scrollPercent = mPercent
                    if (mPercent > 1) {
                        mPercent = 1f
                    }
                    if (mPercent < 0.9) {
                        Log.d("TTT", "if mPercent - mPercent =  ${mPercent} ")
                    } else {
                        Log.d("TTT", "else mPercent - mPercent =  ${mPercent} ")
                    }

                    // 计算alpha通道值
                    val alpha = mPercent * 255
                    val cd = binding.homeSuspendedRl.background as ColorDrawable
                    val color = cd.color
                    val red = color and 0xff0000 shr 16
                    val green = color and 0x00ff00 shr 8
                    val blue = color and 0x0000ff
                    //设置背景颜色
                    Log.d("TTT", " value = ${Color.argb(alpha.toInt(), red, green, blue)}")
                    binding.homeSuspendedRl.setBackgroundColor(Color.argb(alpha.toInt(), red, green, blue))
                    binding.homeLlFilter.setBackgroundColor(Color.argb(alpha.toInt(), red, green, blue))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
        binding.homeRefreshLayout.setDisableContentWhenRefresh(true) //是否在刷新的时候禁止列表的操作
        binding.homeRefreshLayout.setDisableContentWhenLoading(true) //是否在加载的时候禁止列表的操作
        binding.homeRefreshLayout.setEnableOverScrollBounce(true) //是否启用越界拖动
        binding.homeRefreshLayout.setEnableOverScrollDrag(true) //自定header
        val myRefreshHeader = MyRefreshHeader(MainActivity2@ this)
        myRefreshFooter = MyRefreshFooter(MainActivity2@ this)
        binding.homeRefreshLayout.setRefreshHeader(myRefreshHeader) //设置列表分类类型默认值
        binding.homeRefreshLayout.setRefreshFooter(myRefreshFooter!!)
        binding.homeRefreshLayout.setOnRefreshListener {
            Logger.d("测送数据...非实现方法 onRefresh")
            //重置加载更多样式
            myRefreshFooter!!.setLoadRefresh()
            binding.homeRefreshLayout.finishRefresh()

        }
        binding.homeRefreshLayout.setOnLoadMoreListener {
            binding.homeRefreshLayout.finishLoadMore()

        }
        //下拉隐藏标题块
        binding.homeRefreshLayout.setOnMultiListener(this)
        //设置加载更多
        binding.homeRefreshLayout.setEnableLoadMore(true)



        initBanner()
        initMenu()
        initFilter()
        initActivity()
        initIndex()

    }


    override fun layoutView(): View? {
        return null
    }

    override fun layoutViewGroup(): ViewGroup? {
        return null
    }

    override fun showEmptyView(isShow: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showLoadingView(isShow: Boolean) {
        TODO("Not yet implemented")
    }

    override fun createViewModel(): IndeMainModel {
        return IndeMainModel()
    }


    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
        Log.d("TTT", "onStateChanged - oldState =  ${oldState} -  newState = ${newState}")
        if (newState == RefreshState.PullDownToRefresh) { //隐藏
            binding.homeSuspendedRl.isVisible = false
        } else if (newState == RefreshState.None) { //显示
            binding.homeSuspendedRl.isVisible = true

        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {}
    override fun onLoadMore(refreshLayout: RefreshLayout) {}
    override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean, percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {}
    override fun onHeaderReleased(header: RefreshHeader?, headerHeight: Int, maxDragHeight: Int) {}
    override fun onHeaderStartAnimator(header: RefreshHeader?, headerHeight: Int, maxDragHeight: Int) {}
    override fun onHeaderFinish(header: RefreshHeader?, success: Boolean) {}
    override fun onFooterMoving(footer: RefreshFooter?, isDragging: Boolean, percent: Float, offset: Int, footerHeight: Int, maxDragHeight: Int) {}
    override fun onFooterReleased(footer: RefreshFooter?, footerHeight: Int, maxDragHeight: Int) {}
    override fun onFooterStartAnimator(footer: RefreshFooter?, footerHeight: Int, maxDragHeight: Int) {}
    override fun onFooterFinish(footer: RefreshFooter?, success: Boolean) {}
}