package com.houtrry.appinitdispatchersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.houtrry.appinitdispatcher.AppInitDispatcher
import com.houtrry.appinitdispatcher.Task

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppInitDispatcher.INSTANCE
            .addTask(object : Task {
                override fun run() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
            .addTask(BuglyTask()).start()
    }
}
