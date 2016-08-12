package cn.edu.jumy.girls.common.baseAdapter.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import cn.edu.jumy.girls.common.baseAdapter.recyclerview.base.ItemViewDelegate
import cn.edu.jumy.girls.common.baseAdapter.recyclerview.base.ItemViewDelegateManager
import cn.edu.jumy.girls.common.baseAdapter.recyclerview.base.ViewHolder
import java.util.*

open class MultiItemTypeAdapter<T>(protected var mContext: Context, datas: ArrayList<T>) : RecyclerView.Adapter<ViewHolder>() {
    var mList: ArrayList<T>
        protected set

    protected var mItemViewDelegateManager: ItemViewDelegateManager<T>
    protected var mOnItemClickListener: OnItemClickListener<T>? = null


    init {
        this.mList = datas
        mItemViewDelegateManager = ItemViewDelegateManager()
    }

    override fun getItemViewType(position: Int): Int {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position)
        return mItemViewDelegateManager.getItemViewType(mList[position], position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId = mItemViewDelegateManager.getItemViewLayoutId(viewType)
        val holder = ViewHolder.createViewHolder(mContext, parent, layoutId)
        setListener(parent, holder, viewType)
        return holder
    }

    fun convert(holder: ViewHolder, t: T) {
        mItemViewDelegateManager.convert(holder, t, holder.adapterPosition)
    }

    protected fun isEnabled(viewType: Int): Boolean {
        return true
    }


    protected fun setListener(parent: ViewGroup, viewHolder: ViewHolder, viewType: Int) {
        if (!isEnabled(viewType)) return
        viewHolder.convertView.setOnClickListener { v ->
            if (mOnItemClickListener != null) {
                val position = viewHolder.adapterPosition
                mOnItemClickListener!!.onItemClick(v, viewHolder, mList[position], position)
            }
        }

        viewHolder.convertView.setOnLongClickListener(View.OnLongClickListener { v ->
            if (mOnItemClickListener != null) {
                val position = viewHolder.adapterPosition
                return@OnLongClickListener mOnItemClickListener!!.onItemLongClick(v, viewHolder, mList[position], position)
            }
            false
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        convert(holder, mList[position])
    }

    override fun getItemCount(): Int {
        val itemCount = mList.size
        return itemCount
    }

    fun addItemViewDelegate(itemViewDelegate: ItemViewDelegate<T>): cn.edu.jumy.girls.common.baseAdapter.recyclerview.MultiItemTypeAdapter<*> {
        mItemViewDelegateManager.addDelegate(itemViewDelegate)
        return this
    }

    fun addItemViewDelegate(viewType: Int, itemViewDelegate: ItemViewDelegate<T>): cn.edu.jumy.girls.common.baseAdapter.recyclerview.MultiItemTypeAdapter<*> {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate)
        return this
    }

    protected fun useItemViewDelegateManager(): Boolean {
        return mItemViewDelegateManager.itemViewDelegateCount > 0
    }

    interface OnItemClickListener<T> {
        fun onItemClick(view: View, holder: RecyclerView.ViewHolder, o: T, position: Int)

        fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, o: T, position: Int): Boolean
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.mOnItemClickListener = onItemClickListener
    }
}
