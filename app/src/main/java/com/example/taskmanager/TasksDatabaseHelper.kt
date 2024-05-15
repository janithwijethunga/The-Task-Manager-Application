package com.example.taskmanager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TasksDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // values for database properties
    companion object{
        private const val DATABASE_NAME = "tasksapp.db" // Database file name
        private const val DATABASE_VERSION = 1 // Database version for upgrades
        private const val TABLE_NAME = "alltasks" // Table to store tasks
        private const val COLUMN_ID = "id" // Column for task ID
        private const val COLUMN_TITLE = "title" // Column for task title
        private const val COLUMN_CONTENT = "content" // Column for task content
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // create a new table for tasks
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // drop the existing table
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        // Recreate the database with the new version
        onCreate(db)
    }

    // Inserts a new task into the database
    fun insertTask(task: Task){
        val db = writableDatabase // Get the database with write permission
        // Prepare the values to insert
        val values = ContentValues().apply{
            put(COLUMN_TITLE, task.title)
            put(COLUMN_CONTENT, task.content)
        }
        // Insert the values into the table
        db.insert(TABLE_NAME, null, values)
        db.close() // Close the database connection
    }

    // Retrieves all tasks from the database
    fun getAllTasks(): List<Task> {
        val tasksList = mutableListOf<Task>() // List to hold tasks
        val db = readableDatabase // Get the database with read permission
        val query = "SELECT * FROM $TABLE_NAME" // SQL query to select all tasks
        val cursor = db.rawQuery(query, null) // Execute the query

        while(cursor.moveToNext()){
            // Extract task details from the current row
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            // Create a Task object and add it to the list
            val task = Task(id, title, content)
            tasksList.add(task)
        }
        cursor.close()
        db.close()
        return tasksList
    }

    // Updates an existing task in the database
    fun updateTask(task:Task){
        val db = writableDatabase // Get the database with write permission
        // Prepare the new values for the task
        val values = ContentValues().apply{
            put(COLUMN_TITLE, task.title)
            put(COLUMN_CONTENT, task.content)
        }

        // Define the condition for the update
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(task.id.toString())
        // Update the task in the database
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close() // Close the database connection
    }

    // Retrieves a single task by its ID
    fun getTaskByID(taskID: Int): Task {
        val db = readableDatabase // Get the database with read permission
        // SQL query to select the task with the specified ID
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $taskID"
        val cursor = db.rawQuery(query, null) // Execute the query
        cursor.moveToFirst() // Move to the first row in the result set

        // Extract task details from the current row
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close() // Close the cursor
        db.close() // Close the database connection
        return Task(id, title, content) // Return the task
    }

    // Deletes a task from the database
    fun deleteTask(taskId: Int){
        val db = writableDatabase // Get the database with write permission
        // Define the condition for the deletion
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(taskId.toString())
        // Delete the task from the database
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close() // Close the database connection
    }
}
