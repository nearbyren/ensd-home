package ejiayou.home.module

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.orhanobut.logger.Logger
import ejiayou.common.module.mvvm.BaseBindActivity
import ejiayou.home.export.router.HomeRouterTable
import ejiayou.home.module.databinding.HomeMainActivityBinding
import ejiayou.index.export.router.IndexServiceUtil
import ejiayou.me.export.router.MeServiceUtil
import ejiayou.order.export.router.OrderServiceUtil

/**
 * @author: lr
 * @created on: 2022/7/16 4:42 下午
 * @description:
 */
@Route(path = HomeRouterTable.PATH_HOME_UI_MAIN)
class HomeMainActivity : BaseBindActivity<HomeMainActivityBinding>(), ViewPager.OnPageChangeListener {

    override fun layoutRes(): Int {
        return R.layout.home_main_activity
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun initialize(savedInstanceState: Bundle?) {

        ImmersionBar.with(this).statusBarDarkFont(true,2f).init()
//        binding.homeStart.setOnClickListener {
//            StationServiceUtil.navigateStationDetailPage()
//            val manager = supportFragmentManager
//            val beginTransaction = manager.beginTransaction()
//
//            val fragment = IndexServiceUtil.navigateIndexPage()
//            beginTransaction.add(R.id.home_fragment, fragment, "index")
//            beginTransaction.commit()
//
//        }
        Logger.d("initialize")
        val indexFragment = IndexServiceUtil.navigateIndexPage()
        val orderFragment = OrderServiceUtil.navigateOrderDetailPage()
        val meFragment = MeServiceUtil.navigateMePage()

        val pagers = FragmentPagerItems.with(this)
                .add(R.string.home_index, indexFragment.javaClass)
                .add(R.string.home_order, orderFragment.javaClass)
                .add(R.string.home_me, meFragment.javaClass).create()
        val adapter = FragmentPagerItemAdapter(supportFragmentManager, pagers)

        binding.homeViewPager.offscreenPageLimit = pagers.size
        binding.homeViewPager.adapter = adapter
        binding.homeViewPager.setScanScroll(false)
        binding.homeViewPager.addOnPageChangeListener(this)
        setup(binding.homeTabLayout)
        binding.homeTabLayout.setViewPager(binding.homeViewPager)
    }

    fun setup(layout: SmartTabLayout) {
        layout.setCustomTabView { container, position, adapter ->
            val view: View =
                LayoutInflater.from(MainActivity@ this).inflate(R.layout.home_menu_layout, container, false)
            val icon = view.findViewById<View>(R.id.icon) as ImageView
            val name = view.findViewById<View>(R.id.name) as TextView
            name.text = adapter.getPageTitle(position)
            when (position) {
                0 -> icon.setImageDrawable(resources.getDrawable(R.drawable.home_menu_index_selector))
                1 -> icon.setImageDrawable(resources.getDrawable(R.drawable.home_menu_order_selector))
                2 -> icon.setImageDrawable(resources.getDrawable(R.drawable.home_menu_me_selector))
                else -> throw IllegalStateException("Invalid position: $position")
            }
            view
        }
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


}