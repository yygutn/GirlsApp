package cn.edu.jumy.girls.common.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.Toast
import cn.edu.jumy.girls.BuildConfig
import cn.edu.jumy.girls.R
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby.mvp.MvpView
import com.orhanobut.logger.Logger

/**
 * User: Jumy (yygutn@gmail.com)
 * Date: 16/8/1  上午12:33
 */
open abstract class BaseMvpActivity<V : MvpView, P : MvpBasePresenter<V>> : MvpActivity<V, P>() {

    lateinit var mToolbar: Toolbar

    protected val mContext: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
        setContentView(getLayout())
        viewBinding()
        initToolbar()
    }

    open fun viewBinding(){
        mToolbar = findViewById(R.id.mToolbar) as Toolbar
    }

    open abstract fun getLayout(): Int

    protected open fun getMenuRes(): Int {
        return -1
    };

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (getMenuRes() > 0) {
            menuInflater.inflate(getMenuRes(), menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    fun initToolbar() {
        setSupportActionBar(mToolbar)
    }

    fun setTitle(titleString: String, showHome: Boolean) {
        title = titleString
        supportActionBar?.setDisplayShowHomeEnabled(showHome)
        supportActionBar?.setDisplayHomeAsUpEnabled(showHome)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    fun Activity.showToast(string: String) {
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