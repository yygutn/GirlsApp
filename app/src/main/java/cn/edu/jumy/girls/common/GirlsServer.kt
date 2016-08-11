package cn.edu.jumy.girls.common

import cn.edu.jumy.girls.data.GankData
import cn.edu.jumy.girls.data.GirlsData
import cn.edu.jumy.girls.data.休息视频Data
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by Jumy on 16/8/11 11:26.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
interface GirlsServer {
    @GET("data/福利/{pagesize}/{page}")
    fun getPrettyGirlData(@Path("pagesize") pagesize: Int,
                          @Path("page") page: Int): Observable<GirlsData>

    @GET("data/休息视频/{pagesize}/{page}")
    fun get休息视频Data(@Path("pagesize") pagesize: Int,
                    @Path("page") page: Int): Observable<休息视频Data>

    @GET("day/{year}/{month}/{day}")
    fun getGankData(@Path("year") year: Int,
                    @Path("month") month: Int,
                    @Path("day") day: Int): Observable<GankData>
}