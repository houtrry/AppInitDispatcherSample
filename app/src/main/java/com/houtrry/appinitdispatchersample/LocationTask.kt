package com.houtrry.appinitdispatchersample

import com.houtrry.appinitdispatcher.Task
import com.houtrry.appinitdispatcher.showLog

/**
 * @author: houtrry
 * @date: 2019/12/13 10:34
 * @version: $
 * @description:
 */
class LocationTask : Task {
    override fun run() {
        showLog("LocationTask is running start, ${Thread.currentThread().name}, ${Thread.currentThread().id}")

        try {
            Thread.sleep(1000)
        } catch (e:Exception) {
            e.printStackTrace()
        }

        showLog("LocationTask is running end")
    }

    override fun dependOn(): List<Class<out Task>>? {
        return listOf(OkHttpTask::class.java)
    }
}