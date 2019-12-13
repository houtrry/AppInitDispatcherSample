package com.houtrry.appinitdispatchersample

import com.houtrry.appinitdispatcher.Task
import com.houtrry.appinitdispatcher.showLog

/**
 * @author: houtrry
 * @date: 2019/12/13 10:54
 * @version: $
 * @description:
 */
class JPushTask:Task {
    override fun run() {
        showLog("JPushTask is running start, ${Thread.currentThread().name}, ${Thread.currentThread().id}")

        try {
            Thread.sleep(800)
        } catch (e:Exception) {
            e.printStackTrace()
        }

        showLog("JPushTask is running end")
    }
}