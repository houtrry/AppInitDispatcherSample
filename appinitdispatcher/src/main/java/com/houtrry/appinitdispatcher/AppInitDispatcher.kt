package com.houtrry.appinitdispatcher

import android.content.Context
import android.os.Handler
import android.os.Looper
import java.util.concurrent.*
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

    private lateinit var context:Context

    companion object {
        val INSTANCE: AppInitDispatcher by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { AppInitDispatcher() }
    }

    private val threadPoolExecutor: ThreadPoolExecutor by lazy { getThreadPool() }

    fun addTask(task: Task): AppInitDispatcher {
        tasks.add(task)
        return this
    }

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    fun clear(): AppInitDispatcher {
        tasks.clear()
        return this
    }

    fun start(): AppInitDispatcher {
        showLog("AppInitDispatcher start")
        val startTime = System.currentTimeMillis()
        val sortedMap = directedAcyclicGraphSort(tasks)
        val sortedList = outputSortList(sortedMap)
        showLog("AppInitDispatcher start, directedAcyclicGraphSort and outputSortList cost time is ${System.currentTimeMillis() - startTime}")

        sortedList.forEach {
            it.task?.also { task ->
                if (task.runOn() == Task.THREAD_TYPE_MAIN) {
                    mainHandle.post {
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
        bag.await()
        showLog("${bag.task} start running, and await cost time is ${System.currentTimeMillis() - startTime}")
        bag.doTask()
        showLog("${bag.task} end running, and start release countDown")
//        val outTask = bag.outTask
//        if (!outTask.isNullOrEmpty()) {
//            outTask.forEach {
//                val outBag = map[it]
//                outBag?.countDown()
//            }
//        }
        bag.outTask?.let {
            it.forEach {cls->
                map[cls]?.countDown()
            }
        }
        showLog("${bag.task} end running, and cost time is ${System.currentTimeMillis() - startTime}")
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