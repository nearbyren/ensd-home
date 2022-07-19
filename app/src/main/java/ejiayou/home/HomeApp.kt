package ejiayou.home

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import ejiayou.common.module.http.CorHttp

/**
 * @author:
 * @created on: 2022/7/11 16:28
 * @description:
 */
class HomeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        CorHttp.getInstance().init(this)
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }
}