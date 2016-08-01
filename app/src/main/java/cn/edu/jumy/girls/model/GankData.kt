package cn.edu.jumy.girls.model

import cn.edu.jumy.girls.model.bean.Gank
import com.google.gson.annotations.SerializedName

/**
 * All Data数据模型（所有干货List）
 * Created by xybcoder on 2016/3/1

 * 数据格式见：http://gank.io/api/day/2015/08/07

 */
class GankData : BaseData() {
    var category: List<String>? = null
    var results: Result? = null

    inner class Result {
        @SerializedName("Android") var androidList: List<Gank>? = null
        @SerializedName("休息视频") var 休息视频List: List<Gank>? = null
        @SerializedName("iOS") var iOSList: List<Gank>? = null
        @SerializedName("福利") var 妹纸List: List<Gank>? = null
        @SerializedName("拓展资源") var 拓展资源List: List<Gank>? = null
        @SerializedName("瞎推荐") var 瞎推荐List: List<Gank>? = null
        @SerializedName("App") var appList: List<Gank>? = null
        @SerializedName("前端") var 前端List: List<Gank>? = null
    }

    override fun toString(): String {
        return "GankData{" +
                "category=" + category +
                ", results=" + results +
                '}'
    }
}