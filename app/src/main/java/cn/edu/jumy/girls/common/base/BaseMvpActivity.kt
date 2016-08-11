package cn.edu.jumy.girls.common.base

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import butterknife.BindView
import butterknife.ButterKnife
import cn.edu.jumy.girls.R
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby.mvp.MvpView

/**
 * User: Jumy (yygutn@gmail.com)
 * Date: 16/8/1  上午12:33
 */
open abstract class BaseMvpActivity<V : MvpView, P : MvpBasePresenter<V>> : MvpActivity<V, P>() {
    @BindView(R.id.recView)
    protected lateinit var mListView: RecyclerView
    @BindView(R.id.toolbar)
    protected lateinit var mToolbar: Toolbar

    protected val mContext: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
        ButterKnife.bind(this)
        initToolbar()
    }

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
        if (mToolbar == null) {
            throw NullPointerException("please add a ToolBar in your layout")
        }
        setSupportActionBar(mToolbar)
    }

    fun setTitle(titleString: String, showHome: Boolean) {
        setTitle(titleString)
        supportActionBar?.setDisplayShowHomeEnabled(showHome)
        supportActionBar?.setDisplayHomeAsUpEnabled(showHome)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}