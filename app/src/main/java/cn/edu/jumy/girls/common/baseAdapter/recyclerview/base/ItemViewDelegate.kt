package cn.edu.jumy.girls.common.baseAdapter.recyclerview.base


interface ItemViewDelegate<T> {

    val itemViewLayoutId: Int

    fun isForViewType(item: T, position: Int): Boolean

    fun convert(holder: ViewHolder, t: T, position: Int)


}
