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
import android.widget.Toast

import cn.edu.jumy.girls.common.GirlsApp


/**
 * Created by drakeet on 9/27/14.
 */
class ToastUtils private constructor() {

    companion object {

        private fun show(context: Context, resId: Int, duration: Int) {
            Toast.makeText(context, resId, duration).show()
        }

        private fun show(context: Context, message: String, duration: Int) {
            Toast.makeText(context, message, duration).show()
        }

        fun showShort(resId: Int) {
            Toast.makeText(GirlsApp.mContext, resId, Toast.LENGTH_SHORT).show()
        }

        fun showShort(message: String) {
            Toast.makeText(GirlsApp.mContext, message, Toast.LENGTH_SHORT).show()
        }

        fun showLong(resId: Int) {
            Toast.makeText(GirlsApp.mContext, resId, Toast.LENGTH_LONG).show()
        }

        fun showLong(message: String) {
            Toast.makeText(GirlsApp.mContext, message, Toast.LENGTH_LONG).show()
        }

        fun showLongLong(message: String) {
            showLong(message)
            showLong(message)
        }

        fun showLongLong(resId: Int) {
            showLong(resId)
            showLong(resId)
        }

        fun showLongLongLong(resId: Int) {
            showLong(resId)
            showLong(resId)
            showShort(resId)
        }

        fun showLongLongLong(message: String) {
            showLong(message)
            showLong(message)
            showShort(message)
        }
    }
}
