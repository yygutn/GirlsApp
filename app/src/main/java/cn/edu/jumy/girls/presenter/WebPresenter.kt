package cn.edu.jumy.girls.presenter

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.edu.jumy.girls.ui.view.IWebView
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter

/**
 * Created by Jumy on 16/8/11 16:00.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class WebPresenter : MvpBasePresenter<IWebView> {
    var mContext: Context

    constructor(context: Context) : super() {
        mContext = context
    }

    fun setUpView(webView: WebView) {
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.setAppCacheEnabled(true)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.setSupportZoom(true)
        webView.setWebViewClient(JClient())
    }

    fun loadUrl(webView: WebView, url: String) {
        webView.loadUrl(url)
    }

    inner class JClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (TextUtils.isEmpty(url)) {
                return true
            }
            if (Uri.parse(url).host == "github.com") {
                return false
            }
            view.loadUrl(url)
            return true
        }
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            getView()?.showRefresh()
            super.onPageStarted(view, url, favicon)
        }

        override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
            getView()?.hideRefresh()
            getView()?.showLoadErrorMessage(description!!)
            super.onReceivedError(view, errorCode, description, failingUrl)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            getView()?.hideRefresh()
            super.onPageFinished(view, url)
        }
    }
}