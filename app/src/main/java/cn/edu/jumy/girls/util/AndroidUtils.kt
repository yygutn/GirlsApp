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

package cn.edu.jumy.girls.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.widget.TextView
import cn.edu.jumy.girls.R

/**
 * Created by GuDong on 10/14/15 22:22.
 * Contact with gudong.name@gmail.com.
 */
class AndroidUtils {
    companion object{
        fun copyToClipBoard(context: Context, text: String, success: String) {
            val clipData = ClipData.newPlainText("meizhi_copy", text)
            val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            manager.primaryClip = clipData
            ToastUtils.showShort(success)
        }

        /**
         * get accent color
         * @param context
         * *
         * @return
         */
        fun getAccentColor(context: Context): Int {
            val typedValue = TypedValue()
            val theme = context.theme
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
            return typedValue.data
        }
        fun isAndroidL():Boolean{
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        }

        /**
         * base on Toolbar get the TitleView by reflect way
         * @param obj Toolbar
         * *
         * @return the title text view in Toolbar
         */
        fun getTitleViewInToolbar(obj: Toolbar): TextView {
            var textView: TextView? = null
            try {
                val title = obj.javaClass.getDeclaredField("mTitleTextView")
                title.isAccessible = true
                textView = title.get(obj) as TextView
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            return textView!!
        }

        /**
         * get app version info
         * @param context context
         * *
         * @return app version info if occur exception return unknow
         */
        fun getAppVersion(context: Context): String {
            try {
                val info = context.packageManager.getPackageInfo(context.packageName, 0)
                return info.versionName
            } catch (e: Exception) {
                e.printStackTrace()
                return "未知"
            }

        }

        fun setCurrentVersion(context: Context, version: String) {
            putStringPreference(context, "current_version", version)
        }

        fun getLocalVersion(context: Context): String {
            return getStringPreference(context, "current_version", "")
        }

        fun getStringPreference(context: Context, key: String, def: String): String {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getString(key, def)
        }

        fun putStringPreference(context: Context, key: String, value: String) {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putString(key, value).apply()
        }
    }

}
