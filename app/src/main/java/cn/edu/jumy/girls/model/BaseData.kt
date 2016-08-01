package cn.edu.jumy.girls.model

import java.io.Serializable

/**
 * Created by Jumy on 16/8/1 17:20.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
open class BaseData : Serializable {
    val error: Boolean = false

    override fun toString(): String {
        return "BaseData(error=$error)"
    }

}