package com.mahmood.taskmanager.domain.usecase

import com.mahmood.taskmanager.domain.entity.Task
import com.mahmood.taskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) = taskRepository.updateTask(task)
}
