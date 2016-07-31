package cn.edu.jumy.girls.common.base

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import java.lang.ref.WeakReference
import java.util.*

/**
 * User: Jumy (yygutn@gmail.com)
 * Date: 16/7/31  下午10:55
 */
class AppManager {
    companion object {
        private val mStack: Stack<WeakReference<AppCompatActivity>> = Stack()
        fun getInstance(): AppManager = AppManager()
    }

    /**
     * 添加到栈
     */
    fun addActivity(activity : AppCompatActivity){
        mStack.add(WeakReference(activity))
    }

    /**
     * 获取当前Activity
     */
    fun getCurActivity(): AppCompatActivity {
        return mStack.lastElement().get()
    }

    /**
     * 结束当前Activity
     */
    fun finishCurActivity(){
        finishActivity(mStack.lastElement().get())
    }

    /**
     * 结束指定Activity
     */
    private fun finishActivity(activity: AppCompatActivity){
        activity.finish()
        activity = null!!
    }

    /**
     * 结束指定Class的Activity
     */
    fun finishActivity(cls: Class<*>) {
        mStack.forEach { activityRef ->
            if (activityRef.get().javaClass.equals(cls)){
                mStack.remove(activityRef)
                finishActivity(activityRef.get())
                return
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAll(){
        mStack.forEach { activityRef ->
            activityRef.get().finish()
        }
    }

    /**
     * 退出应用程序
     */
    fun AppExit(context: Context){
        finishAll()
        val activityMgr: android.app.ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        activityMgr.killBackgroundProcesses(context.packageName)
        System.exit(0)
    }
}