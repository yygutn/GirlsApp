package cn.edu.jumy.girls.data.entity

import cn.edu.jumy.girls.common.GirlsCategory
import java.io.Serializable
import java.util.*

/**
 * Created by Jumy on 16/8/11 11:28.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class Gank: Soul(),Cloneable,Serializable{
    var used = false;
    var type: String = ""
    var url: String = ""
    var who: String = ""
    var desc: String = ""
    var updatedAt: Date? = null
    var createdAt: Date? = null
    var publishedAt: Date? = null

    var isHeader = false
    fun is妹子(): Boolean {
        return type == GirlsCategory.福利.name
    }
    public override fun clone(): Gank {
        var gank: Gank? = null
        try {
            gank = super.clone() as Gank
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        return gank!!
    }
}