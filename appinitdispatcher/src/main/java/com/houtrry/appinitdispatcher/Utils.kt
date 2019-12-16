package com.houtrry.appinitdispatcher

import android.util.Log
import java.util.*

/**
 * @author: houtrry
 * @time: 2019/12/13
 * @desc:
 */
fun directedAcyclicGraphSort(sources: MutableList<Task>): MutableMap<Class<out Task>, Bag> {

    val map = mutableMapOf<Class<out Task>, Bag>()
    sources.forEach {
        var bag: Bag
        val cls = it::class.java
        if (map.containsKey(cls)) {
            bag = map[cls]!!
            if (bag.task == null) {
                bag.task = it
                bag.inTask = it.dependOn()
                bag.inDegree = if (it.dependOn() == null) 0 else it.dependOn()!!.size
                map[cls] = bag
            }
        } else {
            bag = Bag(it)
            bag.inTask = it.dependOn()
            bag.inDegree = if (it.dependOn() == null) 0 else it.dependOn()!!.size
            map[cls] = bag
        }


        val dependOnList = it.dependOn()
        dependOnList?.forEach { clazz ->
            val outBag: Bag
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

fun outputSortList(map: MutableMap<Class<out Task>, Bag>): LinkedList<Bag> {
    val headerList = LinkedList<Bag>()
    map.forEach { (key, value) ->
        if (value.inDegree == 0) {
            headerList.add(value)
        }
    }
    return sortBagList(map, LinkedList<Bag>(), headerList)
}

private fun sortBagList(map: MutableMap<Class<out Task>, Bag>, sortList: LinkedList<Bag>, zeroInDegreeList: LinkedList<Bag>): LinkedList<Bag> {
    if (zeroInDegreeList.isEmpty()) {
        return sortList
    }
    val nextList = LinkedList<Bag>()
    zeroInDegreeList.forEach {
        sortList.add(it)
        val outTask = it.outTask
        if (!outTask.isNullOrEmpty()) {
            outTask.forEach { cls ->
                val bag = map[cls]
                if (bag != null) {
                    if (!nextList.contains(bag)) {
                        if (bag.inDegree > 1) {
                            bag.inDegree--
                            map[cls] = bag
                        } else {
                            nextList.add(bag)
                        }
                    }
                }
            }
        }
    }
    showLog("sortBagList, sortList: $sortList, nextList: $nextList, map: $map")
    return sortBagList(map, sortList, nextList)
}


fun showLog(msg: String) {
    Log.d("===>>>", msg)
}

fun showErrorLog(msg: String) {
    Log.e("===>>>", msg)
}