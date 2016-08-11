package cn.edu.jumy.girls.presenter

import android.content.Context
import cn.edu.jumy.girls.common.GirlsRetrofit
import cn.edu.jumy.girls.data.GankData
import cn.edu.jumy.girls.data.entity.Gank
import cn.edu.jumy.girls.ui.view.MainView
import cn.edu.jumy.girls.util.AndroidUtils
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import java.util.*

/**
 * Created by Jumy on 16/8/11 13:26.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class MainPresenter : MvpBasePresenter<MainView<Gank>> {
    companion object {
        val DAY_OF_MILLISECOND = 24 * 60 * 60 * 1000;
        val mService = GirlsRetrofit.instance.mService
    }

    var mContext: Context

    constructor(mContext: Context) : super() {
        this.mContext = mContext
    }

    private var mCurrentDate = Date()
    var mGankList: ArrayList<Gank> = ArrayList()
    private var mCountOfGetMoreDataEmpty = 0

    private var hasLoadMoreData = false


    //check version info ,if the version info has changed,we need pop a dialog to show change log info
    fun checkVersionInfo() {
        val currentVersion = AndroidUtils.getAppVersion(mContext)
        val localVersionName = AndroidUtils.getLocalVersion(mContext)
        if (localVersionName != currentVersion) {
            view?.showChangeLogInfo("changelog.html")
            AndroidUtils.setCurrentVersion(mContext, currentVersion)
        }
    }

    fun shouldRefillData(): Boolean {
        return !hasLoadMoreData
    }

    fun getData(date: Date) {
        mCurrentDate = date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        mService.getGankData(year, month, day)
                .observeOn(AndroidSchedulers.mainThread())
                .map(Func1<GankData, GankData.Result> { gankData ->
                    gankData.results
                })
                .map(Func1<GankData.Result, ArrayList<Gank>> { result ->
                    addAllResults(result)
                })
                .subscribe(object : Subscriber<ArrayList<Gank>>() {
                    override fun onCompleted() {
                        // after get data complete, need put off time one day
                        mCurrentDate = Date(date.time - DAY_OF_MILLISECOND)
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onNext(list: ArrayList<Gank>) {
                        // some day the data will be return empty like sunday, so we need get after day data
                        if (list.isEmpty()) {
                            getData(Date(date.time - DAY_OF_MILLISECOND))
                        } else {
                            mCountOfGetMoreDataEmpty = 0
                            view?.fillData(list)
                        }
                        view?.getDataFinish()
                    }
                })
    }

    fun getDataMore() {
        val calendar = Calendar.getInstance()
        calendar.time = mCurrentDate
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        mService.getGankData(year, month, day)
                .observeOn(AndroidSchedulers.mainThread())
                .map(Func1<GankData, GankData.Result> { gankData ->
                    gankData.results
                })
                .map(Func1<GankData.Result, ArrayList<Gank>> { result ->
                    addAllResults(result)
                })
                .subscribe(object : Subscriber<ArrayList<Gank>>() {
                    override fun onCompleted() {
                        // after get data complete, need put off time one day
                        mCurrentDate = Date(mCurrentDate.time - DAY_OF_MILLISECOND)
                        // now user has execute getMoreData so this flag will be set true
                        //and now when user pull down list we would not refill data
                        hasLoadMoreData = true
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(list: ArrayList<Gank>) {
                        //when this day is weekend , the list will return empty(weekend has not gank info,the editors need rest)
                        if (list.isEmpty()) {
                            //record count of empty day
                            mCountOfGetMoreDataEmpty += 1
                            //if empty day is more than five,it indicate has no more data to show
                            if (mCountOfGetMoreDataEmpty >= 5) {
                                view?.hasNoMoreData()
                            } else {
                                // we need look forward data
                                getDataMore()
                            }
                        } else {
                            mCountOfGetMoreDataEmpty = 0
                            view?.appendMoreDataToView(list)
                        }
                        view?.getDataFinish()
                    }
                })
    }

    private fun addAllResults(results: GankData.Result): ArrayList<Gank> {
        mGankList.clear()
        if (results.androidList != null) mGankList.addAll(results.androidList!!)
        if (results.iOSList != null) mGankList.addAll(results.iOSList!!)
        if (results.appList != null) mGankList.addAll(results.appList!!)
        if (results.拓展资源List != null) mGankList.addAll(results.拓展资源List!!)
        if (results.瞎推荐List != null) mGankList.addAll(results.瞎推荐List!!)
        if (results.休息视频List != null) mGankList.addAll(results.休息视频List!!)
        // make meizi data is in first position
        if (results.妹纸List != null) mGankList.addAll(0, results.妹纸List!!)
        return mGankList
    }

}