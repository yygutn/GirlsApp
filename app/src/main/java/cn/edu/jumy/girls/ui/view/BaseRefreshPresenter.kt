package cn.edu.jumy.girls.ui.view

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter

/**
 * Created by Jumy on 16/8/11 12:43.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
open abstract class BaseRefreshPresenter : MvpBasePresenter<BaseRefreshView>() {

    abstract fun loadData()
}