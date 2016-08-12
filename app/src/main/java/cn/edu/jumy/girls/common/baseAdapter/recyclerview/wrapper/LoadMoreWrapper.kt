package cn.edu.jumy.girls.common.baseAdapter.recyclerview.wrapper

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewGroup

import cn.edu.jumy.girls.common.baseAdapter.recyclerview.base.ViewHolder
import cn.edu.jumy.girls.common.baseAdapter.recyclerview.utils.WrapperUtils



class LoadMoreWrapper<T>(private val mInnerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mLoadMoreView: View? = null
    private var mLoadMoreLayoutId: Int = 0

    private fun hasLoadMore(): Boolean {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0
    }


    private fun isShowLoadMore(position: Int): Boolean {
        return hasLoadMore() && position >= mInnerAdapter.itemCount
    }

    override fun getItemViewType(position: Int): Int {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE
        }
        return mInnerAdapter.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            val holder: ViewHolder
            if (mLoadMoreView != null) {
                holder = ViewHolder.createViewHolder(parent.context, mLoadMoreView!!)
            } else {
                holder = ViewHolder.createViewHolder(parent.context, parent, mLoadMoreLayoutId)
            }
            return holder
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isShowLoadMore(position)) {
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener!!.onLoadMoreRequested()
            }
            return
        }
        mInnerAdapter.onBindViewHolder(holder, position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, object : WrapperUtils.SpanSizeCallback {
            override fun getSpanSize(layoutManager: GridLayoutManager, oldLookup: GridLayoutManager.SpanSizeLookup, position: Int): Int {
                if (isShowLoadMore(position)) {
                    return layoutManager.spanCount
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position)
                }
                return 1
            }
        })
    }


    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder?) {
        mInnerAdapter.onViewAttachedToWindow(holder)

        if (isShowLoadMore(holder!!.layoutPosition)) {
            setFullSpan(holder)
        }
    }

    private fun setFullSpan(holder: RecyclerView.ViewHolder) {
        val lp = holder.itemView.layoutParams

        if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {

            lp.isFullSpan = true
        }
    }

    override fun getItemCount(): Int {
        return mInnerAdapter.itemCount + if (hasLoadMore()) 1 else 0
    }


    interface OnLoadMoreListener {
        fun onLoadMoreRequested()
    }

    private var mOnLoadMoreListener: OnLoadMoreListener? = null

    fun setOnLoadMoreListener(loadMoreListener: OnLoadMoreListener?): LoadMoreWrapper<*> {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener
        }
        return this
    }

    fun setLoadMoreView(loadMoreView: View): LoadMoreWrapper<*> {
        mLoadMoreView = loadMoreView
        return this
    }

    fun setLoadMoreView(layoutId: Int): LoadMoreWrapper<*> {
        mLoadMoreLayoutId = layoutId
        return this
    }

    companion object {
        val ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2
    }
}
