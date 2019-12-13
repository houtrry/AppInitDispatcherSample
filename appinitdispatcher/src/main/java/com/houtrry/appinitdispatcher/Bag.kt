package com.houtrry.appinitdispatcher

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

    override fun toString(): String {
        return "->>{task: $task, inDegree: $inDegree, outDegree: $outDegree, outTask: $outTask}<<-"
    }
}