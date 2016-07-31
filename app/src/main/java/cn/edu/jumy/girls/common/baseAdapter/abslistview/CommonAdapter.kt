package cn.edu.jumy.girls.common.baseAdapter.abslistview

import android.content.Context

import cn.edu.jumy.girls.common.baseAdapter.abslistview.base.ItemViewDelegate

abstract class CommonAdapter<T>(mContext: Context,
                                mLayoutId: Int,
                                mDatas: List<T>) : MultiItemTypeAdapter<T>(mContext, mDatas) {

    init {

        addItemViewDelegate(object : ItemViewDelegate<T> {
            override val itemViewLayoutId: Int
                get() = mLayoutId

            override fun isForViewType(item: T, position: Int): Boolean {
                return true
            }

            override fun convert(holder: ViewHolder, t: T, position: Int) {
                this@CommonAdapter.convert(holder, t, position)
            }
        })
    }


    abstract override fun convert(viewHolder: ViewHolder, item: T, position: Int)

}
