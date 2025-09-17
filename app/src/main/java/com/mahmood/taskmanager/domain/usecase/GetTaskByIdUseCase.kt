package com.mahmood.taskmanager.domain.usecase

import com.mahmood.taskmanager.domain.entity.Task
import com.mahmood.taskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(id: Long): Task? = taskRepository.getTaskById(id)
}
