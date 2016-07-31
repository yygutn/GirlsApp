package cn.edu.jumy.girls.common.base

import android.content.Context
import android.os.Bundle
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.hannesdorfmann.mosby.mvp.MvpView

/**
 * User: Jumy (yygutn@gmail.com)
 * Date: 16/8/1  上午12:33
 */
open abstract class BaseMvpActivity<V : MvpView, P : MvpPresenter<V>> : MvpActivity<V, P>() {
    protected val mContext: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
    }
}