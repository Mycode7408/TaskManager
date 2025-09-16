package com.mahmood.taskmanager.domain.usecase

import com.mahmood.taskmanager.domain.entity.Task
import com.mahmood.taskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(query: String): Flow<List<Task>> = taskRepository.searchTasks(query)
}
