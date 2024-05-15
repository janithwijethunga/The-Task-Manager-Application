package com.example.taskmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Binding instance for the main activity layout
    private lateinit var binding: ActivityMainBinding

    // Database helper instance
    private lateinit var db: TasksDatabaseHelper

    // Adapter for managing tasks in RecyclerView
    private lateinit var tasksAdapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize database helper
        db = TasksDatabaseHelper(this)

        //all tasks retrieved from database
        tasksAdapter = TasksAdapter(db.getAllTasks(), this)

        // Set up RecyclerView with a linear layout manager and tasks adapter
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

        // Refresh task data when activity resumes
        tasksAdapter.refreshData(db.getAllTasks())
    }
}
