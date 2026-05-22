package com.example.syntecxhub_todo_app

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etTask: EditText
    private lateinit var btnAdd: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var tvTaskCount: TextView

    private lateinit var taskAdapter: TaskAdapter

    private val taskList = mutableListOf<String>()

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTask = findViewById(R.id.etTask)
        btnAdd = findViewById(R.id.btnAdd)
        recyclerView = findViewById(R.id.recyclerView)
        tvEmpty = findViewById(R.id.tvEmpty)
        tvTaskCount = findViewById(R.id.tvTaskCount)

        sharedPreferences = getSharedPreferences("tasks", MODE_PRIVATE)

        loadTasks()

        taskAdapter = TaskAdapter(taskList) { position ->
            deleteTask(position)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        updateUI()

        btnAdd.setOnClickListener {

            val task = etTask.text.toString().trim()

            if (task.isNotEmpty()) {

                taskList.add(task)

                taskAdapter.notifyItemInserted(taskList.size - 1)

                saveTasks()

                updateUI()

                etTask.text.clear()
            }
        }
    }

    private fun deleteTask(position: Int) {

        taskList.removeAt(position)

        taskAdapter.notifyItemRemoved(position)

        saveTasks()

        updateUI()
    }

    private fun saveTasks() {

        val editor = sharedPreferences.edit()

        editor.putStringSet("task_list", taskList.toSet())

        editor.apply()
    }

    private fun loadTasks() {

        val savedTasks = sharedPreferences.getStringSet("task_list", setOf())

        taskList.addAll(savedTasks!!)
    }

    private fun updateUI() {

        if (taskList.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
        } else {
            tvEmpty.visibility = View.GONE
        }

        tvTaskCount.text = "${taskList.size} Tasks"
    }
}