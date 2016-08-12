/*
 *
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 * Copyright (C) 2015 GuDong <maoruibin9035@gmail.com>
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package cn.edu.jumy.girls.ui.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import cn.edu.jumy.girls.R
import cn.edu.jumy.girls.presenter.DialogPresenter
import cn.edu.jumy.girls.util.DialogUtil
import com.hannesdorfmann.mosby.mvp.MvpView

class WebViewDialog : DialogFragment(), MvpView {
    lateinit var mPresenter: DialogPresenter
    lateinit var mWebView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = DialogPresenter(activity)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val customView: View
        try {
            customView = LayoutInflater.from(activity).inflate(R.layout.dialog_webview, null)
            mWebView = customView.findViewById(R.id.webview) as WebView
            mWebView.addJavascriptInterface(WebAppInterface(customView.context), "Android")
        } catch (e: InflateException) {
            throw IllegalStateException("This device does not support Web Views.")
        }

        return mPresenter.makeOkDialog(this, customView)
    }

    inner class WebAppInterface
    /** Instantiate the interface and set the context  */
    internal constructor(internal var mContext: Context) {

        /** Show a toast from the web page  */
        @JavascriptInterface
        fun showToast(toast: String) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
        }

        /** Show a dialog about app  */
        @JavascriptInterface
        fun showAbout() {
            DialogUtil.showCustomDialog(activity, fragmentManager, getString(R.string.action_about), "about_gank_app.html", "app")
        }

        /** Show a dialog about gank site  */
        @JavascriptInterface
        fun showAboutGank() {
            DialogUtil.showCustomDialog(activity, fragmentManager, getString(R.string.action_about_gank), "about_gank_site.html", "site")
        }
    }

}
