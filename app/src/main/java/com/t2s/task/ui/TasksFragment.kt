package com.t2s.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.t2s.task.adapter.TasksAdapter
import com.t2s.task.databinding.TasksFragBinding

class TasksFragment : Fragment() {

    private lateinit var viewDataBinding: TasksFragBinding
    private lateinit var viewModel: TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = TasksViewModel(requireActivity().application)
        viewDataBinding = TasksFragBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel

        viewDataBinding.tasksList.setHasFixedSize(true)

        viewModel?.getAllTask?.observe(this, Observer { getAllTask ->
            if (!getAllTask.isNullOrEmpty()) {
                val tasksAdapter = TasksAdapter(requireContext(), getAllTask)
                viewDataBinding.tasksList.adapter = tasksAdapter
            }
        })
        viewModel?.isLoadingLiveData?.observe(this, Observer { isLoading ->
            if (isLoading) {
                showProgressBar()
            } else {
                if (viewModel.getAllTask.value.isNullOrEmpty()) {
                    showDataFound()
                } else {
                    showRecycler()
                }
            }
        })

        viewModel?.loadData(requireActivity().application)
    }

    private fun showProgressBar() {
        viewDataBinding.tasksList.visibility = View.GONE
        viewDataBinding.noData.visibility = View.GONE
        viewDataBinding.progressBar.visibility = View.VISIBLE
    }

    private fun showRecycler() {
        viewDataBinding.tasksList.visibility = View.VISIBLE
        viewDataBinding.noData.visibility = View.GONE
        viewDataBinding.progressBar.visibility = View.GONE
    }

    private fun showDataFound() {
        viewDataBinding.tasksList.visibility = View.GONE
        viewDataBinding.noData.visibility = View.VISIBLE
        viewDataBinding.progressBar.visibility = View.GONE
    }
}