package com.houtrry.appinitdispatchersample

import android.app.Application
import com.houtrry.appinitdispatcher.AppInitDispatcher

/**
 * @author: houtrry
 * @date: 2020/9/9 17:50
 * @version: $
 * @description:
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppInitDispatcher.INSTANCE.clear()
                .addTask(ActiveAndroidTask())
                .addTask(ANRWatchDogTask())
                .addTask(BuglyTask())
                .addTask(FrescoTask())
                .addTask(JPushTask())
                .addTask(LocationTask())
                .addTask(LogTask())
                .addTask(MapTask())
                .addTask(OkHttpTask()).start()
    }
}