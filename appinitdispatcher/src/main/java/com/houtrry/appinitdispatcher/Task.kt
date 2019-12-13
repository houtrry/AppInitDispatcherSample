package com.houtrry.appinitdispatcher

/**
 * @author: houtrry
 * @time: 2019/12/12
 * @desc:
 */
interface Task {

    companion object {
        const val THREAD_TYPE_MAIN = 0
        const val THREAD_TYPE_WORK = 1
    }

    fun run()

    fun runOn(): Int {
        return THREAD_TYPE_WORK
    }

    fun dependOn(): List<Class<out Task>>? {
        return null
    }
}