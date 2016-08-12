package cn.edu.jumy.girls.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import cn.edu.jumy.girls.R
import cn.edu.jumy.girls.data.entity.Gank
import cn.edu.jumy.girls.presenter.MainPresenter
import cn.edu.jumy.girls.ui.adapter.MainAdapter
import cn.edu.jumy.girls.ui.view.MainView
import cn.edu.jumy.girls.util.DateUtil
import cn.edu.jumy.girls.util.DialogUtil
import java.util.*

class MainActivity : BaseRefreshMvpActivity<MainView<Gank>, MainPresenter>(), MainAdapter.IClickMainItem, MainView<Gank> {


    private var mHasMoreData = true
    private var mList: ArrayList<Gank> = ArrayList()
    private var mAdapter: MainAdapter = MainAdapter(this, mList)

    private lateinit var mListView: RecyclerView

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun viewBinding() {
        super.viewBinding()
        mListView = findViewById(R.id.mListView) as RecyclerView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecycleView()
        setTitle(getString(R.string.app_name), false)
        getPresenter().checkVersionInfo()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Handler().postDelayed(Runnable {
            showRefresh()
        }, 600)
        getData()
    }

    override fun createPresenter(): MainPresenter {
        return MainPresenter(mContext)
    }

    override fun onRefreshStarted() {
        getData()
    }

    override fun showErrorView(throwable: Throwable) {
        throwable.printStackTrace()
    }


    override fun prepareRefresh(): Boolean {
        return getPresenter().shouldRefillData()
    }

    override fun getMenuRes(): Int {
        return R.menu.menu_main
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_view_list -> startActivity(Intent(this, ListActivity::class.java))
            R.id.action_github_tending -> {
                val url = getString(R.string.url_github_yygutn)
                val title = getString(R.string.action_github_yygutn)
                WebActivity.gotoWebActivity(this, url, title)
            }
            R.id.action_about -> {
                DialogUtil.showCustomDialog(this, supportFragmentManager, getString(R.string.action_about), "about_gank_app.html", "about")
            }
            R.id.action_opinion -> {
                val urlOpinion = getString(R.string.url_github_issue)
                val titleOpinion = getString(R.string.action_github_issue)
                WebActivity.gotoWebActivity(this, urlOpinion, titleOpinion)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData() {
        getPresenter().getData(Date(System.currentTimeMillis()))
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        mListView.layoutManager = layoutManager
        mAdapter.setIClickItem(this)
        mListView.adapter = mAdapter

        mListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val isBottom = layoutManager.findLastCompletelyVisibleItemPosition() >= mAdapter.itemCount - 4
                if (!mSwipeRefreshLayout.isRefreshing && isBottom && mHasMoreData) {
                    showRefresh()
                    getPresenter().getDataMore()
                } else if (!mHasMoreData) {
                    hasNoMoreData()
                }
            }
        })
    }

    override fun onClickGankItemGirl(gank: Gank, viewImage: View, viewText: View) {
        GirlDetailActivity.gotoGirlDetail(this, gank.url, DateUtil.toDate(gank.publishedAt!!), viewImage, viewText)
    }

    override fun onClickGankItemNormal(gank: Gank, view: View) {
        WebActivity.gotoWebActivity(mContext, gank.url, gank.desc)
    }

    override fun fillData(data: ArrayList<Gank>) {
        mAdapter.updateWithClear(data)
    }

    override fun appendMoreDataToView(data: ArrayList<Gank>) {
        mAdapter.update(data)
    }

    override fun hasNoMoreData() {
        mHasMoreData = false
        Snackbar.make(mListView, R.string.no_more_gank, Snackbar.LENGTH_LONG)
                .setAction(R.string.action_to_top) {
                    mListView.layoutManager.smoothScrollToPosition(mListView, null, 0)
                }.show()
    }

    override fun showChangeLogInfo(assetFileName: String) {
        DialogUtil.showCustomDialog(this, supportFragmentManager, getString(R.string.change_log), assetFileName, "")
    }
}
