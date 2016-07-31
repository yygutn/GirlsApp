package cn.edu.jumy.girls.common.baseAdapter.abslistview.base


import cn.edu.jumy.girls.common.baseAdapter.abslistview.ViewHolder

/**
 * Created by zhy on 16/6/22.
 */
interface ItemViewDelegate<T> {

    val itemViewLayoutId: Int

    fun isForViewType(item: T, position: Int): Boolean

    fun convert(holder: ViewHolder, t: T, position: Int)


}
