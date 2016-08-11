package cn.edu.jumy.girls.common

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Jumy on 16/8/11 11:48.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class GirlsRetrofit {
    companion object {
        private var monitor = Any()
        val HOST = "http://gank.io/api/"
        var instance: GirlsRetrofit
        get() {
            synchronized(monitor){
                if (instance == null){
                    instance = GirlsRetrofit()
                }
                return instance
            }
        }
        init {
            instance = GirlsRetrofit()
        }
    }


    var mService: GirlsServer
    var retrofit: Retrofit

    constructor() {
        val client: OkHttpClient  = OkHttpClient.Builder().readTimeout(21,TimeUnit.SECONDS).build()
        retrofit = Retrofit.Builder()
                .baseUrl(HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        mService = retrofit.create(GirlsServer::class.java)
    }
}