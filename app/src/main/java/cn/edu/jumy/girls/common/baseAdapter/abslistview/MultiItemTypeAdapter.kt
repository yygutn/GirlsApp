package cn.edu.jumy.girls.common.baseAdapter.abslistview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import cn.edu.jumy.girls.common.baseAdapter.abslistview.base.ItemViewDelegate
import cn.edu.jumy.girls.common.baseAdapter.abslistview.base.ItemViewDelegateManager

open class MultiItemTypeAdapter<T>(protected var mContext: Context, protected var mDatas: List<T>) : BaseAdapter() {

    private val mItemViewDelegateManager: ItemViewDelegateManager<T>


    init {
        mItemViewDelegateManager = ItemViewDelegateManager()
    }

    fun addItemViewDelegate(itemViewDelegate: ItemViewDelegate<T>): MultiItemTypeAdapter<*> {
        mItemViewDelegateManager.addDelegate(itemViewDelegate)
        return this
    }

    private fun useItemViewDelegateManager(): Boolean {
        return mItemViewDelegateManager.itemViewDelegateCount > 0
    }

    override fun getViewTypeCount(): Int {
        if (useItemViewDelegateManager())
            return mItemViewDelegateManager.itemViewDelegateCount
        return super.getViewTypeCount()
    }

    override fun getItemViewType(position: Int): Int {
        if (useItemViewDelegateManager()) {
            val viewType = mItemViewDelegateManager.getItemViewType(mDatas[position], position)
            return viewType
        }
        return super.getItemViewType(position)
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val layoutId = mItemViewDelegateManager.getItemViewLayoutId(mDatas[position], position)

        val viewHolder = ViewHolder[mContext, convertView, parent, layoutId, position]
        convert(viewHolder, getItem(position), position)
        return viewHolder.convertView
    }

    protected open fun convert(viewHolder: ViewHolder, item: T, position: Int) {
        mItemViewDelegateManager.convert(viewHolder, item, position)
    }

    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getItem(position: Int): T {
        return mDatas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


}
