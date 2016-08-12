package cn.edu.jumy.girls.presenter

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import cn.edu.jumy.girls.R
import cn.edu.jumy.girls.ui.fragment.WebViewDialog
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby.mvp.MvpView
import java.io.BufferedReader
import java.io.InputStreamReader

class DialogPresenter : MvpBasePresenter<MvpView>{
    private var mContext: Context

    constructor(mContext: Context) : super() {
        this.mContext = mContext
    }

    companion object{
        private val EXTRA_DIALOG_TITLE = "DIALOG_TITLE"
        private val EXTRA_HTML_FILE_NAME = "HTML_FILE_NAME"
        private val EXTRA_ACCENT_COLOR = "ACCENT_COLOR"
        private val KEY_UTF_8:String = "UTF"+"_8"

        fun create(dialogTitle: String, htmlFileName: String, accentColor: Int): WebViewDialog {
            val dialog = WebViewDialog()
            val args = Bundle()
            args.putString(EXTRA_DIALOG_TITLE, dialogTitle)
            args.putString(EXTRA_HTML_FILE_NAME, htmlFileName)
            args.putInt(EXTRA_ACCENT_COLOR, accentColor)
            dialog.arguments = args
            return dialog
        }
    }

    fun makeOkDialog(fragment: Fragment, customView: View): AlertDialog {
        val dialogTitle = fragment.arguments.getString(EXTRA_DIALOG_TITLE)
        val htmlFileName = fragment.arguments.getString(EXTRA_HTML_FILE_NAME)
        val accentColor = fragment.arguments.getInt(EXTRA_ACCENT_COLOR)

        val webView = customView.findViewById(R.id.webview) as WebView
        setWebView(webView)
        loadData(webView, htmlFileName, accentColor)

        val dialog = AlertDialog.Builder(mContext).setTitle(dialogTitle).setView(customView).setPositiveButton(android.R.string.ok, null).show()

        return dialog
    }

    /**
     * show positive na
     * @param fragment
     * *
     * @param customView
     * *
     * @return
     */
    fun makeMulActionDialog(fragment: Fragment, customView: View,
                            ok: String, okListener: DialogInterface.OnClickListener,
                            negative: String, negativeListener: DialogInterface.OnClickListener,
                            neutral: String, neutralListener: DialogInterface.OnClickListener): AlertDialog {
        val dialogTitle = fragment.arguments.getString(EXTRA_DIALOG_TITLE)
        val htmlFileName = fragment.arguments.getString(EXTRA_HTML_FILE_NAME)
        val accentColor = fragment.arguments.getInt(EXTRA_ACCENT_COLOR)

        val webView = customView.findViewById(R.id.webview) as WebView
        setWebView(webView)
        loadData(webView, htmlFileName, accentColor)

        val dialog = AlertDialog.Builder(mContext).setTitle(dialogTitle).setView(customView).setPositiveButton(ok, okListener).setNegativeButton(negative, negativeListener).setNeutralButton(neutral, neutralListener).show()

        return dialog
    }

    private fun setWebView(webView: WebView) {
        val settings = webView.settings
        settings.defaultTextEncodingName = KEY_UTF_8
        settings.javaScriptEnabled = true
    }

    private fun loadData(webView: WebView, htmlFileName: String, accentColor: Int) {
        try {
            val buf = StringBuilder()
            val json = mContext.assets.open(htmlFileName)
            val bufferedReader = BufferedReader(InputStreamReader(json, KEY_UTF_8))
            var str: String = bufferedReader.readLine()
            while (!TextUtils.isEmpty(str)) {
                buf.append(str)
                str = bufferedReader.readLine()
            }
            bufferedReader.close()

            val formatLodString = buf.toString().replace("{style-placeholder}", "body { background-color: #ffffff; color: #000; }").replace("{link-color}", colorToHex(shiftColor(accentColor, true))).replace("{link-color-active}", colorToHex(accentColor))
//            webView.loadData(formatLodString,"text/html", KEY_UTF_8)
//            webView.loadDataWithBaseURL(null, formatLodString, "text/html", KEY_UTF_8, null)
            webView.loadUrl("file:///android_asset/$htmlFileName")
            webView.reload()
        } catch (e: Throwable) {
            webView.loadData("<h1>Unable to load</h1><p>" + e.message + "</p>", "text/html", KEY_UTF_8)
        }

    }

    private fun colorToHex(color: Int): String {
        return Integer.toHexString(color).substring(2)
    }

    private fun shiftColor(color: Int, up: Boolean): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= if (up) 1.1f else 0.9f // value component
        return Color.HSVToColor(hsv)
    }
}