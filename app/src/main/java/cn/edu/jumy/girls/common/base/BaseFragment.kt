package cn.edu.jumy.girls.common.base

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import cn.edu.jumy.girls.BuildConfig
import com.orhanobut.logger.Logger

/**
 * User: Jumy (yygutn@gmail.com)
 * Date: 16/8/1  上午12:19
 */
open class BaseFragment: Fragment(){

    protected val mContext: Context = activity

    fun Fragment.showToast(string: String) {
        Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.showDebugLogD(string: String) {
        if (BuildConfig.DEBUG) Logger.d(string)
    }

    fun Fragment.showDebugLogW(string: String) {
        if (BuildConfig.DEBUG) Logger.w(string)
    }

    fun Fragment.showDebugLogW(tag: String, string: String) {
        if (BuildConfig.DEBUG) Logger.t(tag).w(string)
    }

    fun Fragment.showDebugLogE(string: String) {
        if (BuildConfig.DEBUG) Logger.e(string)
    }

    fun Fragment.showDebugLogD(tag: String, string: String) {
        if (BuildConfig.DEBUG) Logger.t(tag).d(string)
    }
}