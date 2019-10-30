package com.t2s.task.data

import com.t2s.task.helper.Result
import com.t2s.task.helper.Result.Error
import com.t2s.task.helper.Result.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository internal constructor(
    private val tasksDao: TasksDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getTasks(): Result<List<Task>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(tasksDao.getTasks())
        } catch (e: Exception) {
            Error(e)
        }
    }

    suspend fun saveTask(taskList: List<TaskRepo>) = withContext(ioDispatcher) {
        for (task in taskList) {
            tasksDao.insertAllTask(Task(task.id, task.name, task.price))
        }
    }

    suspend fun deleteAllTasks() = withContext(ioDispatcher) {
        tasksDao.deleteTasks()
    }

    suspend fun getRowCount(): Result<Int> = withContext(ioDispatcher) {
        return@withContext try {
            Success(tasksDao.getRowCount())
        } catch (e: Exception) {
            Error(e)
        }
    }

}