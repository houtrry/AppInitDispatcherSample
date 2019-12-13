package com.houtrry.appinitdispatchersample

import com.houtrry.appinitdispatcher.Task
import com.houtrry.appinitdispatcher.showLog

/**
 * @author: houtrry
 * @date: 2019/12/13 10:34
 * @version: $
 * @description:
 */
class OkHttpTask : Task {
    override fun run() {
        showLog("OkHttpTask is running start, ${Thread.currentThread().name}, ${Thread.currentThread().id}")

        try {
            Thread.sleep(3000)
        } catch (e:Exception) {
            e.printStackTrace()
        }

        showLog("OkHttpTask is running end")
    }

    override fun dependOn(): List<Class<out Task>>? {
        return listOf(ActiveAndroidTask::class.java, LogTask::class.java)
    }
}
