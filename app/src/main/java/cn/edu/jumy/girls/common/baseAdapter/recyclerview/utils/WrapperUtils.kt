package cn.edu.jumy.girls.common.baseAdapter.recyclerview.utils

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

class WrapperUtils {
    open interface SpanSizeCallback {
        fun getSpanSize(layoutManager: GridLayoutManager, oldLookup: GridLayoutManager.SpanSizeLookup, position: Int): Int
    }

    companion object {
        fun onAttachedToRecyclerView(innerAdapter: RecyclerView.Adapter<*>, recyclerView: RecyclerView, callback: SpanSizeCallback) {
            innerAdapter.onAttachedToRecyclerView(recyclerView)

            val layoutManager = recyclerView.layoutManager
            if (layoutManager is GridLayoutManager) {
                val spanSizeLookup = layoutManager.spanSizeLookup

                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return callback.getSpanSize(layoutManager, spanSizeLookup, position)
                    }
                }
                layoutManager.spanCount = layoutManager.spanCount
            }
        }

        fun setFullSpan(holder: RecyclerView.ViewHolder) {
            val lp = holder.itemView.layoutParams

            if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {

                lp.isFullSpan = true
            }
        }
    }
}
