package cn.edu.jumy.girls.data

import android.os.Parcel
import android.os.Parcelable

class GirlsBean {

    /**
     * error : false
     * results : [{"_id":"5771d5eb421aa931ddcc50d6","createdAt":"2016-06-28T09:42:03.761Z","desc":"Dagger2图文完全教程","publishedAt":"2016-06-28T11:33:25.276Z","source":"web","type":"Android","url":"https://github.com/luxiaoming/dagger2Demo","used":true,"who":"代码GG陆晓明"},{"_id":"5771c9ca421aa931ca5a7e59","createdAt":"2016-06-28T08:50:18.731Z","desc":"Android Design 设计模板","publishedAt":"2016-06-28T11:33:25.276Z","source":"chrome","type":"Android","url":"https://github.com/andreasschrade/android-design-template","used":true,"who":"代码家"}]
     */

    var isError: Boolean = false

    var results: List<ResultsEntity>? = null

    class ResultsEntity : Parcelable {
        var _id: String? = null
        var createdAt: String? = null
        var desc: String? = null
        var publishedAt: String? = null
        var source: String? = null
        var type: String? = null
        var url: String? = null
        var isUsed: Boolean = false
        var who: String? = null

        override fun describeContents(): Int {
            return 0
        }


        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(this._id)
            dest.writeString(this.createdAt)
            dest.writeString(this.desc)
            dest.writeString(this.publishedAt)
            dest.writeString(this.source)
            dest.writeString(this.type)
            dest.writeString(this.url)
            dest.writeByte(if (this.isUsed) 1.toByte() else 0.toByte())
            dest.writeString(this.who)
        }

        constructor() {
        }

        protected constructor(`in`: Parcel) {
            this._id = `in`.readString()
            this.createdAt = `in`.readString()
            this.desc = `in`.readString()
            this.publishedAt = `in`.readString()
            this.source = `in`.readString()
            this.type = `in`.readString()
            this.url = `in`.readString()
            this.isUsed = `in`.readByte().toInt() != 0
            this.who = `in`.readString()
        }

        companion object {

            val CREATOR: Parcelable.Creator<ResultsEntity> = object : Parcelable.Creator<ResultsEntity> {
                override fun createFromParcel(source: Parcel): ResultsEntity {
                    return ResultsEntity(source)
                }

                override fun newArray(size: Int): Array<ResultsEntity?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}