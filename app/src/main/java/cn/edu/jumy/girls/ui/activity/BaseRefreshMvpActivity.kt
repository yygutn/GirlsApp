package cn.edu.jumy.girls.ui.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import cn.edu.jumy.girls.R
import cn.edu.jumy.girls.common.base.BaseMvpActivity
import cn.edu.jumy.girls.ui.view.BaseRefreshView
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter

/**
 * Created by Jumy on 16/8/11 12:36.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
open abstract class BaseRefreshMvpActivity<V : BaseRefreshView, P : MvpBasePresenter<V>> : BaseMvpActivity<V, P>(), BaseRefreshView {


    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSwipeLayout()
    }

    override fun viewBinding() {
        super.viewBinding()
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout) as SwipeRefreshLayout
    }

    fun initSwipeLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)
        mSwipeRefreshLayout.setOnRefreshListener {
            if (prepareRefresh()) {
                onRefreshStarted()
            } else {
                hideRefresh()
            }
        }
    }


    protected open fun prepareRefresh(): Boolean {
        return true
    }

    /**
     * the method of get data
     */
    protected abstract fun onRefreshStarted()


    override fun getDataFinish() {
        hideRefresh()
    }

    override fun showEmptyView() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRefresh() {
        mSwipeRefreshLayout.isRefreshing = true
    }

    override fun hideRefresh() {
        // 防止刷新消失太快，让子弹飞一会儿. do not use lambda!!
        mSwipeRefreshLayout.postDelayed({
            mSwipeRefreshLayout.isRefreshing = false
        }, 1000)
    }

    fun isRefreshing(): Boolean {
        return mSwipeRefreshLayout.isRefreshing
    }
}