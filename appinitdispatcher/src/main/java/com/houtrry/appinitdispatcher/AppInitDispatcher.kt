package com.houtrry.appinitdispatcher

import android.os.Handler
import android.os.Looper
import java.util.concurrent.*
import java.util.concurrent.locks.Lock
import kotlin.math.max
import kotlin.math.min

/**
 * @author: houtrry
 * @time: 2019/12/12
 * @desc:
 */
class AppInitDispatcher private constructor() {

    /*
    线程池的核心线程数: 2-4
    Runtime.getRuntime().availableProcessors(): CPU核数, 4核CPU就是返回4
     */
    private val CORE_POOL_SIZE = max(2, min(Runtime.getRuntime().availableProcessors() - 1, 4))
    private val tasks: MutableList<Task> = ArrayList()

    private val mainHandle: Handler by lazy { Handler(Looper.getMainLooper()) }


    companion object {
        val INSTANCE: AppInitDispatcher by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { AppInitDispatcher() }
    }

    private val threadPoolExecutor: ThreadPoolExecutor by lazy { getThreadPool() }

    fun addTask(task: Task): AppInitDispatcher {
        tasks.add(task)
        return this
    }

    fun clear(): AppInitDispatcher {
        tasks.clear()
        return this
    }

    fun start(): AppInitDispatcher {
        val sortedMap = directedAcyclicGraphSort(tasks)
        val sortedList = outputSortList(sortedMap)

        sortedList.forEach {
            val task = it.task
            if (task != null) {
                if (task.runOn() == Task.THREAD_TYPE_MAIN) {
                    mainHandle.post{
                        doTask(sortedMap, it)
                    }
                } else {
                    threadPoolExecutor.execute {
                        doTask(sortedMap, it)
                    }
                }
            }
        }


        return this
    }

    private fun doTask(map: MutableMap<Class<out Task>, Bag>, bag: Bag) {
        showLog("${bag.task} start running")
        val startTime = System.currentTimeMillis()
        val lockTask = lockTask(map, bag)
        showLog("${bag.task} start running, and cost time is ${System.currentTimeMillis() - startTime}")
        showLog("${bag.task} doTask, lockTask: $lockTask")
        try {
            bag.doTask()
        } finally {
            unlocks(lockTask)
        }
        showLog("${bag.task} end running, and cost time is ${System.currentTimeMillis() - startTime}")
    }

    private fun unlocks(lockTask: MutableList<Lock>) {
        if (lockTask.isNullOrEmpty()) {
            return
        }
        lockTask.forEach {
            it.unlock()
        }
    }

    private fun lockTask(map: MutableMap<Class<out Task>, Bag>, bag: Bag):MutableList<Lock> {

        val inTask = bag.inTask
        val outTask = bag.outTask
        showLog("===>>>lockTask, bag: ${bag.task}, threadName: ${Thread.currentThread().name}")
        showLog("===>>>lockTask, bag: ${bag.task}, inTask: $inTask")
        showLog("===>>>lockTask, bag: ${bag.task}, outTask: $outTask")

        val lockList= mutableListOf<Lock>()

        if (!inTask.isNullOrEmpty()) {
            inTask.forEach {
                val targetBag = map[it]
                targetBag?.apply {
                    showLog("===>>>lockTask, bag: ${bag.task}, targetBag: $targetBag")
                    lockList.add(targetBag.lock())
                }
            }
        }

        if (!outTask.isNullOrEmpty()) {
            showLog("===>>>lockTask, bag: ${bag.task}, lock self: $bag")
            lockList.add(bag.lock())
        }
        showLog("===>>>lockTask, bag: ${bag.task}, lockList: $lockList, threadName: ${Thread.currentThread().name}")
        return lockList
    }

    private fun getThreadPool(): ThreadPoolExecutor {
        val threadPoolExecutor = ThreadPoolExecutor(
                CORE_POOL_SIZE,
                CORE_POOL_SIZE,
                60,
                TimeUnit.SECONDS,
                LinkedBlockingDeque(512),
                Executors.defaultThreadFactory(),
                RejectedExecutionHandler { _, _ ->
                    showErrorLog("RejectedExecutionHandler")
                }
        )
        showLog("core pool size is $CORE_POOL_SIZE, and core is ${Runtime.getRuntime().availableProcessors()}")
        threadPoolExecutor.allowCoreThreadTimeOut(true)
        return threadPoolExecutor
    }
}