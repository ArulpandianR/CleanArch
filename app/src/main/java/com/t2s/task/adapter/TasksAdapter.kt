package com.t2s.task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.t2s.task.R
import com.t2s.task.data.Task
import com.t2s.task.helper.TaskHelper
import kotlinx.android.synthetic.main.task_item.view.*

class TasksAdapter(private val context: Context, private val taskList: List<Task>) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text =TaskHelper.changeTextColor(
                "Name : ",
                taskList[position].name!!,
                ContextCompat.getColor(context, R.color.header_title_color)
            )
        holder.price.text =TaskHelper.changeTextColor(
                "Price : ",
                taskList[position].price!!,
                ContextCompat.getColor(context, R.color.header_title_color)
            )
    }

    override fun getItemCount(): Int {
        return taskList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.title_text
        val price: TextView = itemView.title_price

    }
}