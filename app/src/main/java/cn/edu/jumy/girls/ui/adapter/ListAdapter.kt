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
import cn.edu.jumy.girls.util.DateUtil
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
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

    override fun convert(holder: ViewHolder, item: Girl, position: Int) {
        val imageView: RatioImageView = holder.getView<RatioImageView>(R.id.iv_index_photo)
        Picasso.with(mContext)
                .load(item.url)
                .centerCrop()
                .resize(200,200)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        imageView.colorFilter = mColorFilter
                    }

                    override fun onError() {
                    }
                })
        holder.setText(R.id.tv_time, DateUtil.toDate(item.publishedAt!!))
        if (mIClickItem != null) {
            imageView.setOnClickListener(View.OnClickListener {
                mIClickItem!!.onClickPhoto(position, imageView, holder.getView(R.id.tv_time))
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