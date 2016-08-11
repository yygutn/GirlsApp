package cn.edu.jumy.girls.data

import cn.edu.jumy.girls.data.entity.Gank
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Jumy on 16/8/11 11:37.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class GankData : BaseData() {
    var category: ArrayList<String> = ArrayList()
    var results: Result = Result()

    public class Result {
        @SerializedName("Android") var androidList: ArrayList<Gank>? = null
        @SerializedName("休息视频") var 休息视频List: ArrayList<Gank>? = null
        @SerializedName("iOS") var iOSList: ArrayList<Gank>? = null
        @SerializedName("福利") var 妹纸List: ArrayList<Gank>? = null
        @SerializedName("拓展资源") var 拓展资源List: ArrayList<Gank>? = null
        @SerializedName("瞎推荐") var 瞎推荐List: ArrayList<Gank>? = null
        @SerializedName("App") var appList: ArrayList<Gank>? = null
    }
}