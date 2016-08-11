package cn.edu.jumy.girls.common.baseAdapter.recyclerview

import android.content.Context
import android.view.LayoutInflater

import cn.edu.jumy.girls.common.baseAdapter.recyclerview.base.ItemViewDelegate
import cn.edu.jumy.girls.common.baseAdapter.recyclerview.base.ViewHolder
import java.util.*

/**
 * Created by zhy on 16/4/9.
 */
open abstract class CommonAdapter<T>(mContext: Context,
                                mLayoutId: Int,
                                mDatas: ArrayList<T>) : MultiItemTypeAdapter<T>(mContext, mDatas) {
    protected var mInflater: LayoutInflater

    init {
        mInflater = LayoutInflater.from(mContext)

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

    protected abstract fun convert(holder: ViewHolder, t: T, position: Int)


}
