package com.houtrry.appinitdispatcher

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

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

    private val lock: Lock = ReentrantLock()

    fun doTask() {
        task?.run()
    }

    fun lock():Lock {
        lock.lock()
        return lock
    }

    fun unlock() {
        lock.unlock()
    }

    override fun toString(): String {
        return "->>{task: $task, inDegree: $inDegree, outDegree: $outDegree, outTask: $outTask, inTask: $inTask}<<-"
    }
}