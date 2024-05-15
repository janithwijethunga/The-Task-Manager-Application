package com.example.taskmanager

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskmanager.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var db: TasksDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up view binding
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize database helper
        db = TasksDatabaseHelper(this)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        //click listener for save button
        binding.SaveButton.setOnClickListener{
            // Get task title and content from EditText fields
            val title = binding.TitleEditText.text.toString()
            val content = binding.ContentEditText.text.toString()


            val task = Task(0, title, content)


            db.insertTask(task)

            finish()
            Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show()
        }
    }
}
