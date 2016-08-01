package cn.edu.jumy.girls.model

import cn.edu.jumy.girls.model.bean.Gank

/**
 * 通用(Android ,ios,前端，拓展资源，休息视频)数据模型
 * Created by xybcoder on 16/3/1.
 */
class GanHuoData : BaseData() {
    var results: List<Gank>? = null

    override fun toString(): String {
        return "GanHuoData{" +
                "results=" + results +
                '}'
    }
}