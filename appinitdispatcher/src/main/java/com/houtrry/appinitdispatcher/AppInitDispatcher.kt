package com.houtrry.appinitdispatcher

/**
 * @author: houtrry
 * @time: 2019/12/12
 * @desc:
 */
class AppInitDispatcher private constructor(){

    companion object {
        val INSTANCE : AppInitDispatcher by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { AppInitDispatcher() }
    }

    fun addTask(task: Task): AppInitDispatcher {

        return this
    }

    fun start(): AppInitDispatcher {
        return this
    }
}