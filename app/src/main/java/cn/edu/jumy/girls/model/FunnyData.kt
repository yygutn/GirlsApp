package cn.edu.jumy.girls.model

import cn.edu.jumy.girls.model.bean.Gank

/**
 * 休息视频数据模型
 * Created by xybcoder on 2016/3/1.
 */
class FunnyData : BaseData() {
    var results: List<Gank>? = null

    override fun toString(): String {
        return "FunnyData{" +
                "results=" + results +
                '}'
    }
}