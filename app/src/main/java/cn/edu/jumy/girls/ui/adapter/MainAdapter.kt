package cn.edu.jumy.girls.ui.adapter

import android.content.Context
import android.graphics.ColorFilter
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.edu.jumy.girls.R
import cn.edu.jumy.girls.common.GirlsCategory
import cn.edu.jumy.girls.common.baseAdapter.recyclerview.MultiItemTypeAdapter
import cn.edu.jumy.girls.common.baseAdapter.recyclerview.base.ViewHolder
import cn.edu.jumy.girls.data.entity.Gank
import cn.edu.jumy.girls.ui.widget.RatioImageView
import cn.edu.jumy.girls.util.DateUtil
import cn.edu.jumy.girls.util.StringStyleUtils
import com.squareup.picasso.Picasso
import com.squareup.picasso.Callback
import java.util.*

/**
 * Created by Jumy on 16/8/11 14:40.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class MainAdapter : MultiItemTypeAdapter<Gank> {

    companion object{
        private var mIClickItem: IClickMainItem? = null
    }

    //blur meizi
    private val mColorFilter: ColorFilter

    constructor(mContext: Context, datas: ArrayList<Gank>) : super(mContext, datas) {
        val array = floatArrayOf(
                1f, 0f, 0f, 0f, -70f,
                0f, 1f, 0f, 0f, -70f,
                0f, 0f, 1f, 0f, -70f,
                0f, 0f, 0f, 1f, 0f)
        mColorFilter = ColorMatrixColorFilter(ColorMatrix(array))
    }

    override fun getItemViewType(position: Int): Int {
        val gank = mList.get(position)
        if (gank.is妹子()) {
            return EItemType.ITEM_TYPE_GIRL.ordinal
        } else if (gank.isHeader) {
            return EItemType.ITEM_TYPE_CATEGORY.ordinal
        } else {
            return EItemType.ITEM_TYPE_NORMAL.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView:View
        if (viewType == EItemType.ITEM_TYPE_GIRL.ordinal) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.gank_item_girl,null)
            return ViewHolder.createViewHolder(mContext, itemView)
        } else if (viewType == EItemType.ITEM_TYPE_CATEGORY.ordinal) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.gank_item_category,null)
            return ViewHolder.createViewHolder(mContext, itemView)
        } else {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.gank_item_normal,null)
            return ViewHolder.createViewHolder(mContext, itemView)
        }
    }

    /**
     * convert
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Gank = mList[position]
        val viewType = getItemViewType(position)
        if (viewType == EItemType.ITEM_TYPE_GIRL.ordinal) {
            val img: RatioImageView = holder.getView<RatioImageView>(R.id.iv_index_photo);
            img.setOriginalSize(200, 100)
            holder.setText(R.id.tv_video_name, DateUtil.toDate(item.publishedAt!!))
            Picasso.with(mContext)
                    .load(item.url)
                    .into(img, object : Callback {
                        override fun onSuccess() {
                            img.colorFilter = mColorFilter
                        }

                        override fun onError() {
                        }
                    })
            holder.setOnClickListener(R.id.iv_index_photo, View.OnClickListener { view->
                mIClickItem?.onClickGankItemGirl(item,img,view)
            })

        } else if (viewType == EItemType.ITEM_TYPE_CATEGORY.ordinal) {
            holder.setText(R.id.tv_category,item.type)
        } else if (viewType == EItemType.ITEM_TYPE_NORMAL.ordinal) {
            holder.setText(R.id.tv_gank_title,StringStyleUtils.getGankInfoSequence(mContext,item).toString())
            holder.setOnClickListener(R.id.ll_gank_parent, View.OnClickListener { view ->
                mIClickItem?.onClickGankItemNormal(item,view)
            })
        }
    }


    /**
     * the type of RecycleView item
     */
    private enum class EItemType {
        ITEM_TYPE_GIRL,
        ITEM_TYPE_NORMAL,
        ITEM_TYPE_CATEGORY
    }

    /**
     * add data append to history data*
     * @param data new data
     */
    fun update(data: ArrayList<Gank>) {
        formatGankData(data)
        notifyDataSetChanged()
    }

    /**
     * before add data , it will remove history data
     * @param data
     */
    fun updateWithClear(data: ArrayList<Gank>) {
        mList.clear()
        update(data)
    }


    /**
     * filter list and add category entity into list
     * @param data source data
     */
    private fun formatGankData(data: ArrayList<Gank>) {
        //Insert headers into list of items.
        var lastHeader = ""
        for (i in data.indices) {
            val gank = data[i]
            val header = gank.type
            if (!gank.is妹子() && !TextUtils.equals(lastHeader, header)) {
                // Insert new header view.
                val gankHeader: Gank = gank.clone()
                lastHeader = header
                gankHeader.isHeader = true
                mList.add(gankHeader)
            }
            gank.isHeader = false
            mList.add(gank)
        }
    }

    /**
     * get a init Gank entity
     * @return gank entity
     */
    private fun getDefGankGirl(): Gank {
        val gank = Gank()
        gank.publishedAt = Date(System.currentTimeMillis())
        gank.url = "empty"
        gank.type = GirlsCategory.福利.name
        return gank
    }

    fun setIClickItem(IClickItem: IClickMainItem) {
        mIClickItem = IClickItem
    }
    interface IClickMainItem {
        /**
         * click gank girl info item
         * @param gank
         * *
         * @param viewImage
         * *
         * @param viewText
         */
        fun onClickGankItemGirl(gank: Gank, viewImage: View, viewText: View)

        /**
         * click gank normal info item
         * @param gank
         * *
         * @param view
         */
        fun onClickGankItemNormal(gank: Gank, view: View)
    }
}