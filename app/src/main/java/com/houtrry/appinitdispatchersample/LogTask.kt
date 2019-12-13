package com.houtrry.appinitdispatchersample

import com.houtrry.appinitdispatcher.Task
import com.houtrry.appinitdispatcher.showLog

/**
 * @author: houtrry
 * @date: 2019/12/13 10:41
 * @version: $
 * @description:
 */
class LogTask : Task {
    override fun run() {
        showLog("LogTask is running start, ${Thread.currentThread().name}, ${Thread.currentThread().id}")
    }

    override fun runOn(): Int {
        return Task.THREAD_TYPE_MAIN
    }
}