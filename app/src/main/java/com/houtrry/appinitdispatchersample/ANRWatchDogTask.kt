package com.houtrry.appinitdispatchersample

import com.houtrry.appinitdispatcher.Task
import com.houtrry.appinitdispatcher.showLog

/**
 * @author: houtrry
 * @date: 2019/12/13 10:54
 * @version: $
 * @description:
 */
class ANRWatchDogTask:Task {
    override fun run() {
        showLog("ANRWatchDogTask is running start, ${Thread.currentThread().name}, ${Thread.currentThread().id}")

        try {
            Thread.sleep(500)
        } catch (e:Exception) {
            e.printStackTrace()
        }

        showLog("ANRWatchDogTask is running end")
    }
}