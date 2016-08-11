package cn.edu.jumy.girls.ui.view

import cn.edu.jumy.girls.data.entity.Soul
import com.hannesdorfmann.mosby.mvp.MvpView
import java.util.*

/**
 * Created by Jumy on 16/8/11 16:24.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
interface ListViewView<T : Soul> : BaseRefreshView {
    /**
     * load data successfully
     * @param data
     */
    fun fillData(data: ArrayList<T>)

    /**
     * append data to history list(load more)
     * @param data
     */
    fun appendMoreDataToView(data: ArrayList<T>)

    /**
     * no more data
     */
    fun hasNoMoreData()
}