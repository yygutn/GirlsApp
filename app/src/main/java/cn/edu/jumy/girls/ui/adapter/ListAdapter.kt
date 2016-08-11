package cn.edu.jumy.girls.ui.adapter

import android.content.Context
import android.graphics.ColorFilter
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import cn.edu.jumy.girls.R
import cn.edu.jumy.girls.common.baseAdapter.recyclerview.CommonAdapter
import cn.edu.jumy.girls.common.baseAdapter.recyclerview.base.ViewHolder
import cn.edu.jumy.girls.data.entity.Girl
import cn.edu.jumy.girls.ui.widget.RatioImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.gank_item_girl.*
import java.util.*

/**
 * Created by Jumy on 16/8/11 17:20.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class ListAdapter : CommonAdapter<Girl> {
    private var mIClickItem: IClickItem? = null
    fun setIClickItem(IClickItem: IClickItem) {
        mIClickItem = IClickItem
    }

    //blur meizi
    private val mColorFilter: ColorFilter

    constructor(mContext: Context, mLayoutId: Int, mDatas: ArrayList<Girl>) : super(mContext, mLayoutId, mDatas) {
        val array = floatArrayOf(1f, 0f, 0f, 0f, -70f, 0f, 1f, 0f, 0f, -70f, 0f, 0f, 1f, 0f, -70f, 0f, 0f, 0f, 1f, 0f)
        mColorFilter = ColorMatrixColorFilter(ColorMatrix(array))
    }

    override fun convert(viewHolder: ViewHolder, item: Girl, position: Int) {
        val imageView:RatioImageView = viewHolder.getView<RatioImageView>(R.id.iv_index_photo)
        Picasso.with(mContext)
        .load(item.url)
        .into(imageView, object : Callback {
            override fun onSuccess() {
                imageView.setColorFilter(mColorFilter)
            }

            override fun onError() {
            }
        })
        if (mIClickItem != null) {
            imageView.setOnClickListener(View.OnClickListener {
                mIClickItem!!.onClickPhoto(position, imageView, viewHolder.getView(R.id.tv_time))
            })
        }
    }

    /**
     * before add data , it will remove history data
     * @param data
     */
    fun updateWithClear(data: List<Girl>) {
        mList.clear()
        mList.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * add data append to history data
     * @param data new data
     */
    fun update(data: List<Girl>) {
        mList.addAll(data)
        notifyDataSetChanged()
    }

    fun getGirl(position: Int): Girl {
        return mList[position]
    }

    interface IClickItem {
        fun onClickPhoto(position: Int, view: View, textView: View)
    }
}