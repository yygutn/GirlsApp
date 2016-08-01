package cn.edu.jumy.girls.view.splashActivity

import com.hannesdorfmann.mosby.mvp.MvpView

/**
 * Created by Jumy on 16/8/1 11:17.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
interface SplashView: MvpView{
    fun showGirl(girlUrlString: String)
    fun showGirl()
}