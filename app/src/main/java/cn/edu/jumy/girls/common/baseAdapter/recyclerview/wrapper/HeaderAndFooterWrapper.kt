package cn.edu.jumy.girls.common.baseAdapter.recyclerview.wrapper

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import cn.edu.jumy.girls.common.baseAdapter.recyclerview.base.ViewHolder
import cn.edu.jumy.girls.common.baseAdapter.recyclerview.utils.WrapperUtils


/**
 * Created by zhy on 16/6/23.
 */
class HeaderAndFooterWrapper<T>(private val mInnerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mHeaderViews = SparseArrayCompat<View>()
    private val mFootViews = SparseArrayCompat<View>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (mHeaderViews.get(viewType) != null) {
            val holder = ViewHolder.createViewHolder(parent.context, mHeaderViews.get(viewType))
            return holder

        } else if (mFootViews.get(viewType) != null) {
            val holder = ViewHolder.createViewHolder(parent.context, mFootViews.get(viewType))
            return holder
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position)
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - headersCount - realItemCount)
        }
        return mInnerAdapter.getItemViewType(position - headersCount)
    }

    private val realItemCount: Int
        get() = mInnerAdapter.itemCount


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isHeaderViewPos(position)) {
            return
        }
        if (isFooterViewPos(position)) {
            return
        }
        mInnerAdapter.onBindViewHolder(holder, position - headersCount)
    }

    override fun getItemCount(): Int {
        return headersCount + footersCount + realItemCount
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, object : WrapperUtils.SpanSizeCallback {
            override fun getSpanSize(layoutManager: GridLayoutManager, oldLookup: GridLayoutManager.SpanSizeLookup, position: Int): Int {
                val viewType = getItemViewType(position)
                if (mHeaderViews.get(viewType) != null) {
                    return layoutManager.spanCount
                } else if (mFootViews.get(viewType) != null) {
                    return layoutManager.spanCount
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position)
                return 1
            }
        })
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder?) {
        mInnerAdapter.onViewAttachedToWindow(holder)
        val position = holder!!.layoutPosition
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            WrapperUtils.setFullSpan(holder)
        }
    }

    private fun isHeaderViewPos(position: Int): Boolean {
        return position < headersCount
    }

    private fun isFooterViewPos(position: Int): Boolean {
        return position >= headersCount + realItemCount
    }


    fun addHeaderView(view: View) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view)
    }

    fun addFootView(view: View) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view)
    }

    val headersCount: Int
        get() = mHeaderViews.size()

    val footersCount: Int
        get() = mFootViews.size()

    companion object {
        private val BASE_ITEM_TYPE_HEADER = 100000
        private val BASE_ITEM_TYPE_FOOTER = 200000
    }


}
