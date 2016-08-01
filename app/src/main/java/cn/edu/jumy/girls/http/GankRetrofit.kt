package cn.edu.jumy.girls.http

import cn.edu.jumy.girls.common.GankConfig
import cn.edu.jumy.girls.common.GirlsApp
import cn.edu.jumy.girls.model.FunnyData
import cn.edu.jumy.girls.model.GanHuoData
import cn.edu.jumy.girls.model.GankData
import cn.edu.jumy.girls.model.MeiziData
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by Jumy on 16/8/1 13:40.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
interface GankRetrofit {
    // http://gank.io/api/data/数据类型/请求个数/第几页
    @GET(value = "data/福利/${GankConfig.MEIZI_SIZE}/{page}")
    fun getMeiziData(@Path("page") page: Int): Observable<MeiziData>

    @GET("data/休息视频/" + GankConfig.MEIZI_SIZE + "/{page}")
    fun getFunnyData(@Path("page") page: Int): Observable<FunnyData>

    //请求某天干货数据
    @GET("day/{year}/{month}/{day}")
    fun getDailyData(
            @Path("year") year: Int,
            @Path("month") month: Int,
            @Path("day") day: Int): Observable<GankData>

    //请求不同类型干货（通用）
    @GET("data/{type}/" + GankConfig.GANK_SIZE + "/{page}")
    fun getGanHuoData(@Path("type") type: String, @Path("page") page: Int): Observable<GanHuoData>
}