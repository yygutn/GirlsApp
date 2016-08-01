package cn.edu.jumy.girls.common

import android.app.Application
import android.content.Context
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Created by Jumy on 16/8/1 10:44.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class GirlsApp : Application() {
    companion object {
        private var mContext: Context? = null
        fun getInstance(): GirlsApp = GirlsApp()
        fun defaultOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .writeTimeout(3, TimeUnit.SECONDS)
                    .readTimeout(3, TimeUnit.SECONDS)
                    .build()
        }

    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}