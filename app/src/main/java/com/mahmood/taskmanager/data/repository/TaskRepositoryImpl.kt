package com.mahmood.taskmanager.data.repository

import com.mahmood.taskmanager.data.local.TaskDao
import com.mahmood.taskmanager.domain.entity.Priority
import com.mahmood.taskmanager.domain.entity.Task
import com.mahmood.taskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun insertTask(task: Task): Long = taskDao.insertTask(task)

    override suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    override suspend fun getTaskById(id: Long): Task? = taskDao.getTaskById(id)

    override fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getTasksByPriority(priority: Priority): Flow<List<Task>> = 
        taskDao.getTasksByPriority(priority)

    override fun getCompletedTasks(): Flow<List<Task>> = taskDao.getCompletedTasks()

    override fun getPendingTasks(): Flow<List<Task>> = taskDao.getPendingTasks()

    override fun searchTasks(query: String): Flow<List<Task>> = taskDao.searchTasks(query)
}
