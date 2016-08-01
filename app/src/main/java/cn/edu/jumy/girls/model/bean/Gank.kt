package cn.edu.jumy.girls.model.bean

import java.util.*

/**
 * 干货model
 * 数据格式见：http://gank.io/api/data/all/20/2
 * Created by xybcoder on 2016/3/1.
 */
class Gank : Base() {
    var isUsed: Boolean = false
    var type: String? = null//干货类型，如Android，iOS，福利等
    var url: String? = null//链接地址
    var who: String? = null//作者
    var desc: String? = null//干货内容的描述
    var updatedAt: Date? = null
    var createdAt: Date? = null
    var publishedAt: Date? = null
    override fun toString(): String {
        return "Gank(isUsed=$isUsed, type='$type', url='$url', who='$who', desc='$desc', updatedAt=$updatedAt, createdAt=$createdAt, publishedAt=$publishedAt)"
    }


}