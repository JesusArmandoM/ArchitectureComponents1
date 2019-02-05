package com.android.example.architecuralcomponents.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.example.architecuralcomponents.R
import com.android.example.architecuralcomponents.data.Task

class TaskAdapter internal constructor(context: Context): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    var listOfTask = emptyList<Task>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TaskViewHolder {
        val itemView = inflater.inflate(R.layout.list_item, p0, false)

        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int = listOfTask.size

    override fun onBindViewHolder(p0: TaskViewHolder, p1: Int) {
        p0.wordItemView.text = listOfTask[p1].task
    }

    fun setData(list: List<Task>) {
        listOfTask = list
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.txtTask);
    }
}