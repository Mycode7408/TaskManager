package com.mahmood.taskmanager.domain.usecase

import com.mahmood.taskmanager.domain.entity.Task
import com.mahmood.taskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(filterType: FilterType): Flow<List<Task>> = when (filterType) {
        FilterType.ALL -> taskRepository.getAllTasks()
        FilterType.COMPLETED -> taskRepository.getCompletedTasks()
        FilterType.PENDING -> taskRepository.getPendingTasks()
    }
}

enum class FilterType {
    ALL, COMPLETED, PENDING
}
