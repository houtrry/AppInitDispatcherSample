package com.houtrry.appinitdispatchersample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.houtrry.appinitdispatcher.AppInitDispatcher

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun doTask(view: View) {
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
