package com.t2s.task.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.t2s.task.R
import com.t2s.task.helper.TaskHelper

class TaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportFragmentManager.beginTransaction().replace(R.id.hostFragment, TasksFragment())
           .commit()

    }
}
