package com.mahmood.taskmanager.presentation.ui.tasklist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mahmood.taskmanager.R
import com.mahmood.taskmanager.databinding.FragmentTaskListBinding
import com.mahmood.taskmanager.domain.entity.Task
import com.mahmood.taskmanager.domain.usecase.FilterType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskListViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupClickListeners()
        setupObservers()
        setupSearch()
        setupFilterChips()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onTaskClick = { task ->
                val bundle = Bundle()
                bundle.putLong("taskId", task.id)
                findNavController().navigate(R.id.action_taskListFragment_to_taskDetailFragment, bundle)
            },
            onTaskDelete = { task ->
                viewModel.deleteTask(task)
            },
            onTaskToggleComplete = { task ->
                viewModel.toggleTaskCompletion(task)
            }
        )
        
        binding.tasksRecyclerView.adapter = taskAdapter
        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupClickListeners() {
        binding.addTaskFab.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("taskId", 0L)
            findNavController().navigate(R.id.action_taskListFragment_to_taskDetailFragment, bundle)
        }

        binding.viewToggleButton.setOnClickListener {
            viewModel.toggleViewType()
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tasks.collect { tasks ->
                taskAdapter.submitList(tasks)
                binding.emptyStateLayout.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
                binding.progressBar.visibility = View.GONE // Hide loading when data is loaded
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewType.collect { viewType ->
                binding.tasksRecyclerView.layoutManager = if (viewType == ViewType.LIST) {
                    LinearLayoutManager(requireContext())
                } else {
                    GridLayoutManager(requireContext(), 2)
                }
                binding.viewToggleButton.setIconResource(
                    if (viewType == ViewType.LIST) R.drawable.ic_grid_view else R.drawable.ic_list_view
                )
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.searchTasks(s.toString())
            }
        })
    }

    private fun setupFilterChips() {
        binding.allChip.setOnClickListener {
            viewModel.filterTasks(FilterType.ALL)
            clearOtherChips(binding.allChip)
        }

        binding.pendingChip.setOnClickListener {
            viewModel.filterTasks(FilterType.PENDING)
            clearOtherChips(binding.pendingChip)
        }

        binding.completedChip.setOnClickListener {
            viewModel.filterTasks(FilterType.COMPLETED)
            clearOtherChips(binding.completedChip)
        }
    }

    private fun clearOtherChips(selectedButton: com.google.android.material.button.MaterialButton) {
        // Reset all buttons to unselected state
        binding.allChip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.surface_variant))
        binding.allChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
        
        binding.completedChip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.surface_variant))
        binding.completedChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
        
        binding.pendingChip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.surface_variant))
        binding.pendingChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
        
        // Set selected button to selected state
        selectedButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
        selectedButton.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
