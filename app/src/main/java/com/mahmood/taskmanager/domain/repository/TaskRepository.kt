package com.mahmood.taskmanager.domain.repository

import com.mahmood.taskmanager.domain.entity.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun insertTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun getTaskById(id: Long): Task?
    fun getAllTasks(): Flow<List<Task>>
    fun getTasksByPriority(priority: com.mahmood.taskmanager.domain.entity.Priority): Flow<List<Task>>
    fun getCompletedTasks(): Flow<List<Task>>
    fun getPendingTasks(): Flow<List<Task>>
    fun searchTasks(query: String): Flow<List<Task>>
}
