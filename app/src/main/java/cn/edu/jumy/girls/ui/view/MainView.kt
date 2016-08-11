package cn.edu.jumy.girls.ui.view

import cn.edu.jumy.girls.data.entity.Soul
import com.hannesdorfmann.mosby.mvp.MvpView
import java.util.*

/**
 * Created by Jumy on 16/8/11 13:24.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
interface MainView<T: Soul> : BaseRefreshView {
    /**
     * load data successfully
     * @param data
     */
    fun fillData(data: ArrayList<T>)

    /**
     * append data to history list(load more)
     * @param data
     */
    fun appendMoreDataToView(data: ArrayList<T>)

    /**
     * no more data for show and this condition is hard to appear,it need you scroll main view long time
     * I think it has no body do it like this ,even though，I deal this condition also, In case someone does it.
     */
    fun hasNoMoreData()

    /**
     * show change log info in a dialog
     * @param assetFileName the name of local html file like "changelog.html"
     */
    fun showChangeLogInfo(assetFileName: String)
}