package ejiayou.home.module.view.banner

/**
 * @author:
 * @created on: 2022/7/19 16:11
 * @description:
 */
open interface PagerIndicator {
    fun setNum(num: Int)
    fun getTotal(): Int
    fun setSelected(index: Int)
    fun getCurrentIndex(): Int
    fun setNumber(number: Int)
}
