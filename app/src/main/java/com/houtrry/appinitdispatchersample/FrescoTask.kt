package com.houtrry.appinitdispatchersample

import com.houtrry.appinitdispatcher.Task
import com.houtrry.appinitdispatcher.showLog

/**
 * @author: houtrry
 * @date: 2019/12/13 10:53
 * @version: $
 * @description:
 */
class FrescoTask:Task {
    override fun run() {
        showLog("FrescoTask is running start, ${Thread.currentThread().name}, ${Thread.currentThread().id}")

        try {
            Thread.sleep(500)
        } catch (e:Exception) {
            e.printStackTrace()
        }

        showLog("FrescoTask is running end")
    }

    override fun dependOn(): List<Class<out Task>>? {
        return listOf(OkHttpTask::class.java)
    }
}