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
            binding.apply {
                titleText.text = task.title
                descriptionText.text = task.description
                
                // Format dates
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                dueDateText.text = dateFormat.format(Date(task.createdAt))
                
                // Set priority chip
                priorityChip.text = task.priority.displayName
                priorityChip.setChipBackgroundColorResource(
                    when (task.priority) {
                        Priority.HIGH -> R.color.priority_high
                        Priority.MEDIUM -> R.color.priority_medium
                        Priority.LOW -> R.color.priority_low
                    }
                )
                priorityChip.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.white))
                
                // Set priority indicator color
                priorityIndicator.setBackgroundColor(
                    ContextCompat.getColor(binding.root.context,
                        when (task.priority) {
                            Priority.HIGH -> R.color.priority_high
                            Priority.MEDIUM -> R.color.priority_medium
                            Priority.LOW -> R.color.priority_low
                        }
                    )
                )
                
                // Set completion status
                completedCheckBox.isChecked = task.isCompleted
                
                // Show/hide completion overlay
                completionOverlay.visibility = if (task.isCompleted) android.view.View.VISIBLE else android.view.View.GONE
                
                // Set click listeners
                root.setOnClickListener { onTaskClick(task) }
                completedCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked != task.isCompleted) {
                        onTaskToggleComplete(task)
                    }
                }
            }
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
