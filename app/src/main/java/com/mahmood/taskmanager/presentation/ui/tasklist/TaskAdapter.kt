package com.mahmood.taskmanager.presentation.ui.tasklist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmood.taskmanager.R
import com.mahmood.taskmanager.databinding.ItemTaskBinding
import com.mahmood.taskmanager.domain.entity.Priority
import com.mahmood.taskmanager.domain.entity.Task
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(
    private val onTaskClick: (Task) -> Unit,
    private val onTaskDelete: (Task) -> Unit,
    private val onTaskToggleComplete: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.titleText.text = task.title
            binding.descriptionText.text = task.description
            
            // Format dates - use createdAt for both due date and date
            val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
            val fullDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            
            // Due date on the left (showing "Due: " prefix)
            binding.dueDateText.text = "Due: ${dateFormat.format(Date(task.createdAt))}"
            
            // Date on the right (showing just the date)
            binding.dateText.text = dateFormat.format(Date(task.createdAt))
            
            // Set priority chip
            binding.priorityChip.text = task.priority.displayName.uppercase()
            binding.priorityChip.setChipBackgroundColorResource(
                when (task.priority) {
                    Priority.HIGH -> R.color.priority_high
                    Priority.MEDIUM -> R.color.priority_medium
                    Priority.LOW -> R.color.priority_low
                }
            )
            binding.priorityChip.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.white))
            
            // Show/hide completion checkmark
            binding.completionCheckmark.visibility = if (task.isCompleted) android.view.View.VISIBLE else android.view.View.GONE
            
            // Set click listeners
            binding.root.setOnClickListener { onTaskClick(task) }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}
