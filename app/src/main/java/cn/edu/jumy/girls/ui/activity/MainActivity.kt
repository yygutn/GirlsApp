package cn.edu.jumy.girls.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife

import cn.edu.jumy.girls.R
import cn.edu.jumy.girls.data.entity.Gank
import cn.edu.jumy.girls.presenter.MainPresenter
import cn.edu.jumy.girls.ui.adapter.MainAdapter
import cn.edu.jumy.girls.ui.view.MainView
import cn.edu.jumy.girls.util.DialogUtil
import java.util.*

class MainActivity : BaseRefreshMvpActivity<MainView<Gank>, MainPresenter>(),MainAdapter.IClickMainItem,MainView<Gank> {



    private var mHasMoreData = true
    private var mAdapter:MainAdapter? = null
    private var mList: ArrayList<Gank> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(getString(R.string.app_name), false)
        presenter.checkVersionInfo()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Handler().postDelayed(Runnable {
            showRefresh()
        },600)
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
        return presenter.shouldRefillData()
    }

    override fun getMenuRes(): Int {
        return R.menu.menu_main
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
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
        presenter.getData(Date(System.currentTimeMillis()))
    }
    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        mListView.setLayoutManager(layoutManager)
        mAdapter = MainAdapter(this,mList)
        mAdapter?.setIClickItem(this)
        mListView.setAdapter(mAdapter)

        mListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val isBottom = layoutManager.findLastCompletelyVisibleItemPosition() >= mAdapter!!.getItemCount() - 4
                if (!mSwipeRefreshLayout.isRefreshing && isBottom && mHasMoreData) {
                    showRefresh()
                    presenter.getDataMore()
                } else if (!mHasMoreData) {
                    hasNoMoreData()
                }
            }
        })
    }

    override fun onClickGankItemGirl(gank: Gank, viewImage: View, viewText: View) {
    }

    override fun onClickGankItemNormal(gank: Gank, view: View) {
    }


    override fun fillData(data: ArrayList<Gank>) {
        mAdapter?.updateWithClear(data)
    }

    override fun appendMoreDataToView(data: ArrayList<Gank>) {
        mAdapter?.update(data)
    }

    override fun hasNoMoreData() {
        mHasMoreData = false
        Snackbar.make(mListView, R.string.no_more_gank, Snackbar.LENGTH_LONG)
                .setAction(R.string.action_to_top) {
                    mListView.getLayoutManager().smoothScrollToPosition(mListView, null, 0)
                }.show()
    }

    override fun showChangeLogInfo(assetFileName: String) {
        DialogUtil.showCustomDialog(this, supportFragmentManager, getString(R.string.change_log), assetFileName, "")
    }
}
