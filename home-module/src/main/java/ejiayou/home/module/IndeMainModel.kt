package ejiayou.home.module

import androidx.lifecycle.MutableLiveData
import ejiayou.common.module.base.BaseAppViewModel
import ejiayou.home.module.model.FilterItemDto

/**
 * @author: lr
 * @created on: 2022/7/10 2:58 下午
 * @description:
 */
class IndeMainModel : BaseAppViewModel() {

    val filterItemDto = MutableLiveData<ArrayList<FilterItemDto>>()


}