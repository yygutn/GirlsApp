package cn.edu.jumy.girls.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.KeyEvent
import android.view.MenuItem
import android.webkit.WebView
import android.widget.Toast
import butterknife.BindView
import cn.edu.jumy.girls.R
import cn.edu.jumy.girls.presenter.WebPresenter
import cn.edu.jumy.girls.ui.view.IWebView
import cn.edu.jumy.girls.util.AndroidUtils

/**
 * Created by Jumy on 16/8/11 16:04.
 * Copyright (c) 2016, yygutn@gmail.com All Rights Reserved.
 */
class WebActivity:BaseRefreshMvpActivity<IWebView,WebPresenter>(),IWebView{

    @BindView(R.id.wb_content)
    lateinit var mWbContent: WebView
    
    companion object{
        private val EXTRA_URL = "URL"
        private val EXTRA_TITLE = "TITLE"
        fun gotoWebActivity(context: Context, url: String, title: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(EXTRA_URL, url)
            intent.putExtra(EXTRA_TITLE, title)
            context.startActivity(intent)
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_web
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_copy_url->{
                val copyDone = getString(R.string.toast_copy_done)
                AndroidUtils.copyToClipBoard(this, mWbContent.url, copyDone)
                return true
            }
            R.id.action_open_url->{
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                val uri = Uri.parse(mWbContent.url)
                intent.data = uri
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(mContext, R.string.toast_open_fail, Toast.LENGTH_SHORT).show()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getMenuRes(): Int {
        return R.menu.menu_web
    }

    private fun refresh() {
        mWbContent.reload()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mWbContent != null){
            mWbContent.destroy()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mWbContent != null){
            mWbContent.onPause()
        }
    }

    override fun showLoadErrorMessage(message: String) {
        Snackbar.make(mWbContent, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun showErrorView(throwable: Throwable) {
        throwable.printStackTrace()
    }

    override fun onRefreshStarted() {
        refresh()
    }

    override fun createPresenter(): WebPresenter {
        return WebPresenter(mContext)
    }

    override  fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWbContent.canGoBack()) {
            mWbContent.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}