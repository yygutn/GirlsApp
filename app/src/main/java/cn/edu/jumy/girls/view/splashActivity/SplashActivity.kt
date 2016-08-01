package cn.edu.jumy.girls.view.splashActivity

import android.os.Bundle
import android.widget.ImageView
import cn.edu.jumy.girls.R
import cn.edu.jumy.girls.common.base.BaseMvpActivity

/**
 * Created by Jumy on 16/8/1 11:17.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class SplashActivity: BaseMvpActivity<SplashView,SplashPresenter>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }

    override fun createPresenter(): SplashPresenter {
        return SplashPresenter()
    }
}