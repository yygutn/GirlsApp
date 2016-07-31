package cn.edu.jumy.girls.common.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import cn.edu.jumy.girls.BuildConfig
import com.orhanobut.logger.Logger

/**
 * User: Jumy (yygutn@gmail.com)
 * Date: 16/8/1  上午12:10
 */
open class BaseActivity : AppCompatActivity() {
    protected val mContext: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
    }

    fun AppCompatActivity.showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    fun AppCompatActivity.showDebugLogD(string: String) {
        if (BuildConfig.DEBUG) Logger.d(string)
    }

    fun AppCompatActivity.showDebugLogW(string: String) {
        if (BuildConfig.DEBUG) Logger.w(string)
    }

    fun AppCompatActivity.showDebugLogW(tag: String, string: String) {
        if (BuildConfig.DEBUG) Logger.t(tag).w(string)
    }

    fun AppCompatActivity.showDebugLogE(string: String) {
        if (BuildConfig.DEBUG) Logger.e(string)
    }

    fun AppCompatActivity.showDebugLogD(tag: String, string: String) {
        if (BuildConfig.DEBUG) Logger.t(tag).d(string)
    }

}