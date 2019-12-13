package com.houtrry.appinitdispatchersample

import com.houtrry.appinitdispatcher.Task
import com.houtrry.appinitdispatcher.showLog

/**
 * @author: houtrry
 * @date: 2019/12/13 10:34
 * @version: $
 * @description:
 */
class MapTask : Task {
    override fun run() {
        showLog("MapTask is running start, ${Thread.currentThread().name}, ${Thread.currentThread().id}")

        try {
            Thread.sleep(1000)
        } catch (e:Exception) {
            e.printStackTrace()
        }

        showLog("MapTask is running end")
    }

    override fun dependOn(): List<Class<out Task>>? {
        return listOf(FrescoTask::class.java, LocationTask::class.java)
    }
}