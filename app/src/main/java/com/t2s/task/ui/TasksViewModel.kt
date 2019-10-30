package com.t2s.task.ui

import android.app.Application
import android.util.Base64
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.t2s.task.data.Task
import com.t2s.task.data.TaskDatabase
import com.t2s.task.data.TaskRepository
import com.t2s.task.data.TaskResponse
import com.t2s.task.helper.DataConverter
import com.t2s.task.helper.Result
import com.t2s.task.helper.TaskConstant.store
import com.t2s.task.helper.TaskHelper
import com.t2s.task.network.NetworkModule
import com.t2s.task.network.TaskApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.zip.Inflater
import java.util.zip.InflaterOutputStream


class TasksViewModel(application: Application) : AndroidViewModel(application) {

    private var taskRepository: TaskRepository
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val isLoadingLiveData = MediatorLiveData<Boolean>().apply {
        value = true
    }

    var getAllTask =
        MutableLiveData<List<Task>>().apply { value = emptyList() }


    private fun decodeTaskDetails(decodedText: String) {
        viewModelScope.launch {
            val decodedBytes =
                Base64.decode(decodedText, Base64.DEFAULT)//.decodeBase64(decodedText)
            val stream = ByteArrayOutputStream()
            val decompressed = Inflater(false)
            val inflaterOutputStream = InflaterOutputStream(stream, decompressed)
            try {
                inflaterOutputStream.write(decodedBytes)
                inflaterOutputStream.close()
                val outputString = stream.toString()
                if (outputString.isNotEmpty()) {
                    val converterList = DataConverter().getTaskFromJson(outputString)
                    if (!converterList.isNullOrEmpty()) {
                        // saveTaskList(converterList)
                        taskRepository.deleteAllTasks()
                        taskRepository.saveTask(converterList)
                        getTaskList()
                    }
                }
                // logger.info("Data: {}", data)
            } catch (e: Exception) {
                // logger.error(string, e)
            }
        }
    }

    init {
        val wordsDao = TaskDatabase.getDatabase(application).taskDao()
        this.taskRepository = TaskRepository(wordsDao)
    }

    fun loadData(application: Application) {
        viewModelScope.launch {
            val tasksResult = taskRepository.getRowCount()
            if (tasksResult is Result.Success && tasksResult.data <= 0) {
                callRemoteRepo(application)
            } else {
                getTaskList()
            }
        }
    }

    private fun callRemoteRepo(application: Application) {
        if (TaskHelper.isInternetAvailable(application)) {
            val requestInterface = NetworkModule.provideRetrofit(
                NetworkModule.provideOkHttpClient(store)
            ).create(TaskApi::class.java)
            compositeDisposable.add(
                requestInterface.getTaskList().observeOn(
                    AndroidSchedulers.mainThread()
                )?.subscribeOn(
                    Schedulers.io()
                )?.subscribe(
                    this::onTaskSuccess,
                    this::onTaskError
                )!!
            )
        }
    }

    private fun onTaskSuccess(taskResponse: TaskResponse) {
        if (!taskResponse.compressTask.isNullOrEmpty()) {
            decodeTaskDetails(taskResponse.compressTask[0])
        }
    }

    private fun onTaskError(error: Throwable) {
        Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun getTaskList() {
        viewModelScope.launch {
            val tasksResult = taskRepository.getTasks()
            if (tasksResult is Result.Success) {
                getAllTask.value = tasksResult.data
            }
            isLoadingLiveData.value = false
        }
    }

}