package cn.edu.jumy.girls.ui.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView

/**
 * Created by Jumy on 16/8/11 12:37.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
interface BaseRefreshView : MvpView{
    fun getDataFinish()

    fun showEmptyView()

    fun showErrorView(throwable: Throwable)

    fun showRefresh()

    fun hideRefresh()
}