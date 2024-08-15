package com.example.tasks_sheungkit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks_sheungkit.R
import com.example.tasks_sheungkit.models.Task

class TaskAdapter(
    var yourListData:List<Task>,
    var functionFromMainActivity: (Int)->Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.task_row_layout, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return yourListData.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currTask: Task = yourListData.get(position)
        val tvTaskName = holder.itemView.findViewById<TextView>(R.id.tvRowTaskName)
        val tvIsPriorityLabel = holder.itemView.findViewById<ImageView>(R.id.tvRowIsPriority)

        tvTaskName.text = currTask.name

        if (currTask.isPriority == true) {
            tvIsPriorityLabel.setImageResource(R.drawable.baseline_error_24)
        } else {
            tvIsPriorityLabel.setImageDrawable(null)
        }

        val button = holder.itemView.findViewById<ImageButton>(R.id.btnDeleteTask)
        button.setOnClickListener {
            functionFromMainActivity(position)
        }
    }
}