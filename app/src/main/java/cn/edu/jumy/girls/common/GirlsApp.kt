package cn.edu.jumy.girls.common

import android.app.Application
import android.content.Context
import cn.edu.jumy.girls.BuildConfig
import com.orhanobut.logger.LogLevel
import com.orhanobut.logger.Logger

/**
 * Created by Jumy on 16/8/11 11:16.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class GirlsApp : Application() {

    companion object {
        var mContext: Context? = null
            get() {
                return mContext
            }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        //debug模式下输出日志，否则不输出
        if (BuildConfig.DEBUG) {
            Logger.init("Jumy").hideThreadInfo().methodOffset(0)
        } else {
            Logger.init("Release").logLevel(LogLevel.NONE)
        }
    }


}