package com.t2s.task.network

import com.t2s.task.data.TaskResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface TaskApi {

    @GET("/consumer/menu/options")
    fun getTaskList(): Observable<TaskResponse>
}