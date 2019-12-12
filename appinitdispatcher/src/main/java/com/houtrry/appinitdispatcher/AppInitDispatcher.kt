package com.houtrry.appinitdispatcher

import java.util.*

/**
 * @author: houtrry
 * @time: 2019/12/12
 * @desc:
 */
class AppInitDispatcher private constructor() {

    private val tasks: MutableList<Task> = ArrayList()

    companion object {
        val INSTANCE: AppInitDispatcher by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { AppInitDispatcher() }
    }

    fun addTask(task: Task): AppInitDispatcher {
        tasks.add(task)
        return this
    }

    fun start(): AppInitDispatcher {
        val directedAcyclicGraphSort = directedAcyclicGraphSort(tasks)
        return this
    }
}