package ejiayou.home.export.router

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import ejiayou.home.export.router.service.IHomeService

/**
 * @author: lr
 * @created on: 2022/7/16 4:26 下午
 * @description: 提供启动activity  service 等动作
 */
open class HomeServiceUtil {


    companion object {

        fun initService(): IHomeService? {
            var service =
                ARouter.getInstance().build(HomeRouterTable.PATH_SERVICE_HOME).navigation()
            if (service is IHomeService)
                return service
            return null
        }


        fun navigateIndexPage() {
            ARouter.getInstance().build(HomeRouterTable.PATH_HOME_UI_MAIN)
                    .navigation()

        }

        fun navigateStationTestPage() {
            ARouter.getInstance().build(HomeRouterTable.PATH_HOME_UI_TEST)
                    .withString("key1", "哈哈1")
                    .withString("key2", "哈哈2")
                    .navigation()

        }
    }
}