package com.houtrry.appinitdispatcher

import android.util.Log

/**
 * @author: houtrry
 * @time: 2019/12/13
 * @desc:
 */
fun directedAcyclicGraphSort(sources: MutableList<Task>): Map<Class<out Task>, Bag> {

    val map = mutableMapOf<Class<out Task>, Bag>()
    sources.forEach{
        var bag:Bag
        val cls = it::class.java
        if (map.containsKey(cls)) {
            bag = map[cls]!!
            if (bag.task == null) {
                bag.task = it
                bag.inDegree = if (it.dependOn() == null) 0 else it.dependOn()!!.size
                map[cls] = bag
            }
        } else {
            bag = Bag(it)
            bag.inDegree = if (it.dependOn() == null) 0 else it.dependOn()!!.size
            map[cls] = bag
        }


        val dependOnList = it.dependOn()
        dependOnList?.forEach{ clazz ->
            val outBag:Bag
            if (map.containsKey(clazz)) {
                outBag = map[clazz]!!
                outBag.outDegree++
                var outTask = outBag.outTask
                if (outTask == null) {
                    outTask = mutableListOf<Class<out Task>>()
                }
                outTask.add(cls)
                outBag.outTask = outTask
            } else {
                outBag = Bag()
                outBag.outDegree = 1
                outBag.inDegree = 0
                outBag.outTask = mutableListOf(cls)
            }
            map[clazz] = outBag
        }
    }
    showLog("map: $map")

    return map
}


fun showLog(msg: String) {
    Log.d("===>>>", msg)
}