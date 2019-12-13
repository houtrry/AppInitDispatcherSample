package com.houtrry.appinitdispatchersample

import com.houtrry.appinitdispatcher.Task
import com.houtrry.appinitdispatcher.showLog

/**
 * @author: houtrry
 * @date: 2019/12/13 10:54
 * @version: $
 * @description:
 */
class ActiveAndroidTask:Task {
    override fun run() {
        showLog("ActiveAndroidTask is running start, ${Thread.currentThread().name}, ${Thread.currentThread().id}")

        try {
            Thread.sleep(1500)
        } catch (e:Exception) {
            e.printStackTrace()
        }

        showLog("ActiveAndroidTask is running end")
    }

    override fun dependOn(): List<Class<out Task>>? {
        return listOf(LogTask::class.java)
    }
}