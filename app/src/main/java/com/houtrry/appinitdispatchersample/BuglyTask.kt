package com.houtrry.appinitdispatchersample

import com.houtrry.appinitdispatcher.Task
import com.houtrry.appinitdispatcher.showLog

/**
 * @author: houtrry
 * @time: 2019/12/12
 * @desc:
 */
class BuglyTask : Task {
    override fun run() {
        showLog("BuglyTask is running start, ${Thread.currentThread().name}, ${Thread.currentThread().id}")

        try {
            Thread.sleep(1200)
        } catch (e:Exception) {
            e.printStackTrace()
        }

        showLog("BuglyTask is running end")
    }

    override fun dependOn(): List<Class<out Task>>? {
        return listOf(ANRWatchDogTask::class.java)
    }
}