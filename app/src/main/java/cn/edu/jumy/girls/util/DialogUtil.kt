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

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog

import cn.edu.jumy.girls.R
import cn.edu.jumy.girls.presenter.DialogPresenter


/**
 * tool for dialog
 * Created by mao on 7/19/15.
 */
class DialogUtil {
    companion object {
        /**
         * show a dialog which it contain one point message only
         * @param context context
         */
        fun showSinglePointDialog(context: Context, message: String) {
            AlertDialog.Builder(context).setTitle("提示").setMessage(message).setPositiveButton("确定", null).show()
        }


        /**
         * show a custom dialog use a local html file
         * @param context
         * *
         * @param fragmentManager
         * *
         * @param dialogTitle title
         * *
         * @param htmlFileName file name
         * *
         * @param tag
         */
        fun showCustomDialog(context: Context, fragmentManager: FragmentManager, dialogTitle: String, htmlFileName: String, tag: String) {
            val accentColor = AndroidUtils.getAccentColor(context)
            DialogPresenter.create(dialogTitle, htmlFileName, accentColor).show(fragmentManager, tag)
        }
    }
}
