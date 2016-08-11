package cn.edu.jumy.girls.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import butterknife.BindView
import cn.edu.jumy.girls.R
import cn.edu.jumy.girls.data.entity.Gank
import cn.edu.jumy.girls.data.entity.Girl
import cn.edu.jumy.girls.presenter.ListPresenter
import cn.edu.jumy.girls.ui.adapter.ListAdapter
import cn.edu.jumy.girls.ui.view.ListViewView
import kotlinx.android.synthetic.main.activity_view_list.*
import java.util.*

/**
 * Created by Jumy on 16/8/11 16:23.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class ListActivity:BaseRefreshMvpActivity<ListViewView<Girl>,ListPresenter>(),ListViewView<Girl>,ListAdapter.IClickItem{


    private var mAdapter: ListAdapter = ListAdapter(mContext,R.layout.index_item, ArrayList())
    private var mHasMoreData = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_list)
        setTitle("妹子们",true)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        presenter.refillGirls()
    }

    private fun initRecycleView() {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mListView.layoutManager = layoutManager
        mAdapter.setIClickItem(this)
        mListView.adapter = mAdapter

        mListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val isBottom = layoutManager.findLastCompletelyVisibleItemPositions(IntArray(2))[1] >= mAdapter.getItemCount() - 4
                if (!mSwipeRefreshLayout.isRefreshing && isBottom && mHasMoreData) {
                    showRefresh()
                    presenter.getDataMore()
                }
            }
        })
    }
    override fun createPresenter(): ListPresenter {
        return ListPresenter(mContext)
    }

    override fun showErrorView(throwable: Throwable) {
    }

    override fun onRefreshStarted() {
        presenter.refillGirls()
    }

    override fun fillData(data: ArrayList<Girl>) {
        mAdapter.updateWithClear(data)
    }

    override fun prepareRefresh(): Boolean {
        if (presenter.shouldRefillGirls()) {
            presenter.resetCurrentPage()
            if (!isRefreshing()) {
                showRefresh()
            }
            return true
        } else {
            return false
        }
    }

    override fun appendMoreDataToView(data: ArrayList<Girl>) {
        mAdapter.update(data)
    }

    override fun hasNoMoreData() {
        mHasMoreData = false
        Snackbar.make(mListView, R.string.no_more_girls, Snackbar.LENGTH_SHORT)
                .setAction(R.string.action_to_top) {
                    mListView.getLayoutManager().smoothScrollToPosition(mListView, null, 0)
                }.show()
    }
    override fun onClickPhoto(position: Int, view: View, textView: View) {
        val clickGirl = mAdapter.getGirl(position)
        if (clickGirl != null) {
            val gank = Gank()
            gank.type = clickGirl.type
            gank.url = clickGirl.url
            gank.publishedAt = clickGirl.publishedAt
//            GirlFaceActivity.gotoWatchGirlDetail(this, gank.url, DateUtil.toDate(gank.publishedAt), viewImage, viewText)
        }
    }
}