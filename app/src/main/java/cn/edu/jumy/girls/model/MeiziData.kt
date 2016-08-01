package cn.edu.jumy.girls.model

import cn.edu.jumy.girls.model.bean.Meizi

/**
 * 福利数据模型
 * Created by xybcoder on 2016/3/1.
 * 数据格式见：http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
 */
class MeiziData : BaseData() {
    var results: List<Meizi>? = null

    override fun toString(): String {
        return "MeiziData{" +
                "results=" + results +
                '}'
    }
}