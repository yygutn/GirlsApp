package cn.edu.jumy.girls.view

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Jumy on 16/8/1 11:32.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
open abstract class MvpLceRxPresenter<V: MvpLceView<M>,M> : MvpBasePresenter<V>() , MvpPresenter<V>{
    protected var subscriber: Subscriber<M>? = null
    /**
     * Unsubscribes the subscriber and set it to null
     */
    protected fun unsubscribe() {
        if (subscriber != null && !subscriber!!.isUnsubscribed()) {
            subscriber?.unsubscribe()
        }
        subscriber = null
    }

    /**
     * Subscribes the presenter himself as subscriber on the observable

     * @param observable The observable to subscribe
     * *
     * @param pullToRefresh Pull to refresh?
     */
    fun subscribe(observable: Observable<M>, pullToRefresh: Boolean) {

        if (isViewAttached) {
            view?.showLoading(pullToRefresh)
        }

        unsubscribe()

        subscriber = object : Subscriber<M>() {
            private val ptr = pullToRefresh

            override fun onCompleted() {
                this@MvpLceRxPresenter.onCompleted()
            }

            override fun onError(e: Throwable) {
                this@MvpLceRxPresenter.onError(e, ptr)
            }

            override fun onNext(m: M) {
                this@MvpLceRxPresenter.onNext(m)
            }
        }

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber)
    }

    protected fun onCompleted() {
        if (isViewAttached) {
            view?.showContent()
        }
        unsubscribe()
    }

    protected fun onError(e: Throwable, pullToRefresh: Boolean) {
        if (isViewAttached) {
            view?.showError(e, pullToRefresh)
        }
        unsubscribe()
    }

    protected fun onNext(data: M) {
        if (isViewAttached) {
            view?.setData(data)
        }
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        if (!retainInstance) {
            unsubscribe()
        }
    }
}