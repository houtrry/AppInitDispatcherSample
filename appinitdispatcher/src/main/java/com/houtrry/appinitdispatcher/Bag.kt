package com.houtrry.appinitdispatcher

import java.util.concurrent.CountDownLatch

/**
 * @author: houtrry
 * @date: 2019/12/13 14:34
 * @version: $
 * @description:
 */
class Bag constructor(var task: Task? = null){
    var inDegree:Int = 0
    var outDegree:Int = 0
    var outTask:MutableList<Class<out Task>>? = null
    var inTask:List<Class<out Task>>? = null

    private val countDownLatch:CountDownLatch by lazy {
        CountDownLatch(if (inTask.isNullOrEmpty()) 0 else inTask!!.size)
    }

    fun doTask() {
        task?.run()
    }

    fun await() {
        countDownLatch.await()
    }

    fun countDown() {
        countDownLatch.countDown()
    }

    override fun toString(): String {
        return "->>{task: $task, inDegree: $inDegree, outDegree: $outDegree, outTask: $outTask, inTask: $inTask}<<-"
    }
}