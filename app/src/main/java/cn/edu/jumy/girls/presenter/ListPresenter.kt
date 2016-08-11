package cn.edu.jumy.girls.presenter

import android.content.Context
import cn.edu.jumy.girls.common.GirlsRetrofit
import cn.edu.jumy.girls.data.GirlsData
import cn.edu.jumy.girls.data.entity.Gank
import cn.edu.jumy.girls.data.entity.Girl
import cn.edu.jumy.girls.data.entity.Soul
import cn.edu.jumy.girls.data.休息视频Data
import cn.edu.jumy.girls.ui.view.ListViewView
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.functions.Func2
import java.util.*

/**
 * Created by Jumy on 16/8/11 16:24.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class ListPresenter : MvpBasePresenter<ListViewView<Girl>> {
    companion object {
        private val PAGE_SIZE = 10
        val mService = GirlsRetrofit.instance.mService
    }

    private var mCurrentPage = 1
    var mContext: Context

    constructor(mContext: Context) : super() {
        this.mContext = mContext
    }

    fun resetCurrentPage() {
        mCurrentPage = 1
    }

    /**
     * 只有当前只加载了第一页 那么下拉刷新才应该去执行数据请求，如果加载的页数超过两页，
     * 则不去执行重新加载的数据请求，此时的刷新为假刷新，不去请求数据。这是一种良好的用户体验。愚以为~
     * @return
     */
    fun shouldRefillGirls(): Boolean {
        return mCurrentPage <= 2
    }

    /**
     * reload girls data it will clear history girls  so bad !
     */
    fun refillGirls() {
        Observable.zip(mService.getPrettyGirlData(PAGE_SIZE, mCurrentPage),
                mService.get休息视频Data(PAGE_SIZE, mCurrentPage),
                Func2<GirlsData, 休息视频Data, GirlsData> { girlsdata, 休息视频data ->
                    createGirlInfoWith休息视频(girlsdata, 休息视频data)
                })
                .map(Func1<GirlsData, ArrayList<Girl>> { girlsData ->
                    girlsData.results
                })
                .flatMap { girls ->
                    Observable.from(girls)
                }
                .toSortedList(Func2<Girl, Girl, Int> { girl, girl2 ->
                    girl2.publishedAt!!.compareTo(girl.publishedAt!!)
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    (object : Subscriber<ArrayList<Girl>>() {
                        override fun onNext(girls: ArrayList<Girl>?) {
                            if (girls!!.isEmpty()) {
                                view?.hasNoMoreData()
                            } else if (girls.size < PAGE_SIZE) {
                                view?.appendMoreDataToView(girls)
                                view?.hasNoMoreData()
                            } else if (girls.size == PAGE_SIZE) {
                                view?.appendMoreDataToView(girls)
                                mCurrentPage++
                            }
                            view?.getDataFinish()
                        }

                        override fun onCompleted() {
                        }

                        override fun onError(e: Throwable?) {
                            view?.showErrorView(e!!)
                            view?.getDataFinish()
                        }

                    })
                }
    }

    fun getDataMore() {
        Observable.zip(
                mService.getPrettyGirlData(PAGE_SIZE, mCurrentPage),
                mService.get休息视频Data(PAGE_SIZE, mCurrentPage),
                Func2<GirlsData, 休息视频Data, GirlsData> { girlsData, 休息视频data ->
                    createGirlInfoWith休息视频(girlsData, 休息视频data)
                })
                .map(Func1<GirlsData, ArrayList<Girl>> { girlsData ->
                    girlsData.results
                })
                .flatMap(Func1<ArrayList<Girl>, Observable<Girl>> { girls ->
                    Observable.from(girls)
                })
                .toSortedList(Func2 { girl1, girl2 ->
                    girl2.publishedAt?.compareTo(girl1.publishedAt)
                })
                .subscribe {
                    (object : Subscriber<ArrayList<Girl>>() {
                        override fun onNext(girls: ArrayList<Girl>?) {
                            if (girls!!.isEmpty()) {
                                view?.hasNoMoreData()
                            } else if (girls.size < PAGE_SIZE) {
                                view?.appendMoreDataToView(girls)
                                view?.hasNoMoreData()
                            } else if (girls.size == PAGE_SIZE) {
                                view?.appendMoreDataToView(girls)
                                mCurrentPage++
                            }
                            view?.getDataFinish()
                        }

                        override fun onCompleted() {

                        }

                        override fun onError(e: Throwable) {
                            view?.showErrorView(e)
                            view?.getDataFinish()
                        }
                    })
                }

    }

    private fun createGirlInfoWith休息视频(girlData: GirlsData, data: 休息视频Data): GirlsData {
        val restSize = data.results.size
        for (i in 0..girlData.results.size - 1) {
            if (i <= restSize - 1) {
                val girl = girlData.results.get(i)
                girl.desc += " " + data.results[i].desc
            } else {
                break
            }
        }
        return girlData
    }
}
