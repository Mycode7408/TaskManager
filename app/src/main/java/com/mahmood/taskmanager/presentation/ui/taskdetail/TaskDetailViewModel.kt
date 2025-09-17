package com.mahmood.taskmanager.presentation.ui.taskdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmood.taskmanager.domain.entity.Task
import com.mahmood.taskmanager.domain.usecase.AddTaskUseCase
import com.mahmood.taskmanager.domain.usecase.UpdateTaskUseCase
import com.mahmood.taskmanager.domain.usecase.DeleteTaskUseCase
import com.mahmood.taskmanager.domain.usecase.GetTaskByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _taskSaved = MutableLiveData<Boolean>()
    val taskSaved: LiveData<Boolean> = _taskSaved

    private val _taskDeleted = MutableLiveData<Boolean>()
    val taskDeleted: LiveData<Boolean> = _taskDeleted

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loadedTask = MutableLiveData<Task?>()
    val loadedTask: LiveData<Task?> = _loadedTask

    fun saveTask(task: Task) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (task.id == 0L) {
                    addTaskUseCase(task)
                } else {
                    updateTaskUseCase(task)
                }
                _taskSaved.value = true
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                deleteTaskUseCase(task)
                _taskDeleted.value = true
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetTaskSaved() {
        _taskSaved.value = false
    }

    fun resetTaskDeleted() {
        _taskDeleted.value = false
    }

    fun resetError() {
        _error.value = null
    }

    fun loadTask(taskId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val task = getTaskByIdUseCase(taskId)
                _loadedTask.value = task
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load task"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
