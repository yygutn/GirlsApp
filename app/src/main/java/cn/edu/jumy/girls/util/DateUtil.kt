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

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        fun toDate(date: Date): String {
            val dateFormat = SimpleDateFormat("yyyy/MM/dd")
            return dateFormat.format(date)
        }

        fun toDate(date: Date, add: Int): String {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DATE, add)
            return toDate(calendar.time)
        }

        fun getLastdayDate(date: Date): Date {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DATE, -1)
            return calendar.time
        }

        fun getNextdayDate(date: Date): Date {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DATE, 1)
            return calendar.time
        }
    }
}
