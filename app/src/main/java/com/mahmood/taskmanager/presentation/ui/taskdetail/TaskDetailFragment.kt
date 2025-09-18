package com.mahmood.taskmanager.presentation.ui.taskdetail

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mahmood.taskmanager.R
import com.mahmood.taskmanager.databinding.FragmentTaskDetailBinding
import com.mahmood.taskmanager.domain.entity.Priority
import com.mahmood.taskmanager.domain.entity.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskDetailViewModel by viewModels()
    private var taskId: Long = 0L
    private var isEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        taskId = arguments?.getLong("taskId", 0L) ?: 0L
        isEditMode = taskId != 0L
        
        setupUI()
        setupClickListeners()
        setupObservers()
        
        if (isEditMode) {
            loadTask()
        }
    }

    private fun setupUI() {
        // Setup priority dropdown
        val priorities = Priority.values().map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, priorities)
        binding.prioritySpinner.setAdapter(adapter)
        binding.prioritySpinner.setText(Priority.MEDIUM.displayName, false)
        
        // Setup date picker
        binding.dueDateEditText.setOnClickListener {
            showDatePicker()
        }
    }

    private fun setupClickListeners() {
        // Back navigation is handled by the system back button

        binding.saveButton.setOnClickListener {
            saveTask()
        }

        binding.deleteButton.setOnClickListener {
            if (isEditMode) {
                deleteTask()
            } else {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupObservers() {
        viewModel.loadedTask.observe(viewLifecycleOwner) { task ->
            task?.let {
                populateForm(it)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { _ ->
            // Loading state can be handled with UI feedback if needed
        }

        viewModel.taskSaved.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                Snackbar.make(binding.root, "Task saved successfully", Snackbar.LENGTH_SHORT).show()
                findNavController().navigateUp()
                viewModel.resetTaskSaved()
            }
        }

        viewModel.taskDeleted.observe(viewLifecycleOwner) { deleted ->
            if (deleted) {
                Snackbar.make(binding.root, "Task deleted successfully", Snackbar.LENGTH_SHORT).show()
                findNavController().navigateUp()
                viewModel.resetTaskDeleted()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.resetError()
            }
        }
    }

    private fun loadTask() {
        viewModel.loadTask(taskId)
    }

    private fun populateForm(task: Task) {
        binding.titleEditText.setText(task.title)
        binding.descriptionEditText.setText(task.description)
        binding.prioritySpinner.setText(task.priority.displayName, false)
        binding.completedSwitch.isChecked = task.isCompleted
        
        // Format and set due date
        val dateFormat = java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.getDefault())
        binding.dueDateEditText.setText(dateFormat.format(java.util.Date(task.createdAt)))
    }

    private fun saveTask() {
        val title = binding.titleEditText.text.toString().trim()
        val description = binding.descriptionEditText.text.toString().trim()
        // val dueDate = binding.dueDateEditText.text.toString() // Currently not used in validation
        val priorityText = binding.prioritySpinner.text.toString()
        val isCompleted = binding.completedSwitch.isChecked

        if (title.isEmpty()) {
            binding.titleEditText.error = "Title is required"
            return
        }

        val priority = Priority.values().find { it.displayName == priorityText } ?: Priority.MEDIUM

        val task = Task(
            id = taskId,
            title = title,
            description = description,
            priority = priority,
            isCompleted = isCompleted,
            createdAt = if (isEditMode) System.currentTimeMillis() else System.currentTimeMillis()
        )

        viewModel.saveTask(task)
    }

    private fun deleteTask() {
        val task = Task(
            id = taskId,
            title = binding.titleEditText.text.toString(),
            description = binding.descriptionEditText.text.toString(),
            priority = Priority.MEDIUM,
            isCompleted = false
        )
        viewModel.deleteTask(task)
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                binding.dueDateEditText.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
