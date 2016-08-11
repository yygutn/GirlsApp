package cn.edu.jumy.girls.data.entity

import java.util.*

/**
 * Created by Jumy on 16/8/11 11:28.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class Girl : Soul() {
    var used = false
    var type: String = ""
    var url: String = ""
    var who: String = ""
    var desc: String = ""
    var createdAt: Date? = null
    var publishedAt: Date? = null
    var updatedAt: Date? = null
}