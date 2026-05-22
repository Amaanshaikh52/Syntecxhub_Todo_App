package com.example.syntecxhub_todo_app

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val taskList: MutableList<String>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTask: TextView = itemView.findViewById(R.id.tvTask)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
        val checkTask: CheckBox = itemView.findViewById(R.id.checkTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)

        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val task = taskList[position]

        holder.tvTask.text = task

        holder.btnDelete.setOnClickListener {
            onDelete(position)
        }

        holder.checkTask.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                holder.tvTask.paintFlags =
                    holder.tvTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.tvTask.paintFlags =
                    holder.tvTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}