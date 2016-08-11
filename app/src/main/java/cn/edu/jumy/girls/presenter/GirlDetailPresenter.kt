package cn.edu.jumy.girls.presenter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import cn.edu.jumy.girls.R
import cn.edu.jumy.girls.ui.view.GirlDetailView
import cn.edu.jumy.girls.util.TaskUtils
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.orhanobut.logger.Logger
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/**
 * User: Jumy (yygutn@gmail.com)
 * Date: 16/8/11  下午6:37
 */
class GirlDetailPresenter : MvpBasePresenter<GirlDetailView>{
    private var mContext: Context

    constructor(mContext: Context) : super() {
        this.mContext = mContext
    }

    fun saveFace(url: String) {
        if (TextUtils.isEmpty(url)){
            return
        }
        val fileName = url.substring(url.lastIndexOf("/")+1)

    }

    private fun saveImageToSdCard(context: Context, url: String, title: String) {
        TaskUtils.executeAsyncTask(object : AsyncTask<Any, Any, Boolean>() {
            override fun doInBackground(vararg params: Any): Boolean? {
                var bmp: Bitmap? = null
                try {
                    bmp = Picasso.with(context).load(url).get()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                if (bmp == null) {
                    return false
                }

                // 首先保存图片
                val appDir = File(getSDPath(), "Meizhi2")
                if (!appDir.exists()) {
                    val `is` = appDir.mkdir()
                    if (`is`) {
                        Logger.i("create suc")
                    } else {
                        Logger.i("create fail")
                    }
                }
                val file = File(appDir, title)

                try {
                    val fos = FileOutputStream(file)
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.flush()
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    return false
                }

                // 其次把文件插入到系统图库
                try {
                    MediaStore.Images.Media.insertImage(context.contentResolver,
                            file.getAbsolutePath(), title, null)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

                // 最后通知图库更新
                val scannerIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.parse("file://" + file.getAbsolutePath()))
                context.sendBroadcast(scannerIntent)

                return true
            }

            override fun onPostExecute(result: Boolean?) {
                super.onPostExecute(result)
                val msg: String
                if (result!!) {
                    val appDir = File(Environment.getExternalStorageDirectory(), "Meizhi")
                    if (!appDir.exists()) {
                        appDir.mkdir()
                    }
                    msg = String.format(context.getString(R.string.picture_has_save_to),
                            appDir.absolutePath)
                    view?.saveSuccess(msg)
                } else {
                    msg = context.getString(R.string.picture_save_fail)
                    view?.showFailInfo(msg)
                }

            }
        })
    }

    fun getSDPath(): String {
        return Environment.getExternalStorageDirectory().toString()
    }
}