package com.example.taskmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TasksDatabaseHelper
    private lateinit var tasksAdapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TasksDatabaseHelper(this)

        //all tasks retrieved from database
        tasksAdapter = TasksAdapter(db.getAllTasks(), this)


        binding.tasksRecycleview.layoutManager = LinearLayoutManager(this)
        binding.tasksRecycleview.adapter = tasksAdapter

        //add button to navigate to AddTaskActivity
        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume(){
        super.onResume()

        tasksAdapter.refreshData(db.getAllTasks())
    }
}
