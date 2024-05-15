package com.example.taskmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanager.databinding.ActivityUpdateBinding

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: TasksDatabaseHelper
    private var taskId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TasksDatabaseHelper(this)

        // Retrieve the task ID passed from the previous activity
        taskId = intent.getIntExtra("task_id", -1)
        if (taskId == -1) {
            finish()
            return
        }

        val task = db.getTaskByID(taskId)
        binding.TitleUpdateText.setText(task.title)
        binding.ContentUpdateText.setText(task.content)

        // Set an OnClickListener for the save button
        binding.UpdateSaveButton.setOnClickListener {
            val newTitle = binding.TitleUpdateText.text.toString()
            val newContent = binding.ContentUpdateText.text.toString()
            val updatedTask = Task(taskId, newTitle, newContent)
            // Update the task in the database
            db.updateTask(updatedTask)
            // Finish the activity and return to the previous screen
            finish()
            Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show()
        }
    }
}
