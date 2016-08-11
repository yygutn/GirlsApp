package cn.edu.jumy.girls.ui.view

import com.hannesdorfmann.mosby.mvp.MvpView

/**
 * User: Jumy (yygutn@gmail.com)
 * Date: 16/8/11  下午6:36
 */
interface GirlDetailView : MvpView{
    fun saveSuccess(message: String)
    fun showFailInfo(error: String)
}