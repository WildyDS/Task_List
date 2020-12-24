package aleksandr_logunov.task_list.ui.task_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import aleksandr_logunov.task_list.R
import aleksandr_logunov.task_list.base.BaseFragment
import aleksandr_logunov.task_list.data.tasks.ITask
import aleksandr_logunov.task_list.databinding.FragmentTaskListBinding
import aleksandr_logunov.task_list.extensions.ellipsize
import aleksandr_logunov.task_list.helpers.TabSelectedListener
import aleksandr_logunov.task_list.ui.task.TaskFragment
import com.google.android.material.tabs.TabLayout

class TaskListFragment :
    BaseFragment<FragmentTaskListBinding, TaskListViewModel>(R.layout.fragment_task_list) {
    private var columnCount = 1

    override val fabOnClick = View.OnClickListener {
        openTask(action = TaskFragment.Companion.Actions.Create)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        viewModel = ViewModelProvider(this).get(TaskListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentTaskListBinding.bind(view)
        viewModel?.let { vm ->
            vm.loadTasks()
            with(viewBinding!!) {
                rvTaskList.layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                val adapter = TaskListRecyclerViewAdapter(ArrayList(), onTaskPress)
                rvTaskList.adapter = adapter
                vm.tasks.observe(viewLifecycleOwner, { taskList -> adapter.replaceAll(taskList) })
                vm.tabs.observe(viewLifecycleOwner, { tabList ->
                    tabs.removeAllTabs()
                    for (tab in tabList) {
                        tabs.addTab(tabs.newTab().apply {
                            text = tab // TODO: getText(resourceId)
                            tag = tab
                        })
                    }
                    tabs.addOnTabSelectedListener(object : TabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            Log.d(TAG, "Selected tab $tab")
                            // TODO: filter tasks
                            showSnack("Filtering is not implemented yet.")
                        }
                    })
                })
            }
        }
    }


    private val onTaskPress: TaskListRecyclerViewAdapter.OnTaskPress =
        object : TaskListRecyclerViewAdapter.OnTaskPress {
            override fun onPress(task: ITask, position: Int) =
                openTask(task, TaskFragment.Companion.Actions.View)

            override fun onActionPress(task: ITask, position: Int, action: Int) {
                when (action) {
                    R.id.edit_task -> openTask(task, TaskFragment.Companion.Actions.Edit)
                    R.id.delete_task -> viewModel?.deleteTask(task) {
                        showSnack(
                            getString(
                                R.string.task_deleted,
                                task.title.ellipsize(20)
                            )
                        )
                    }
                }
            }
        }

    private fun openTask(task: ITask? = null, action: TaskFragment.Companion.Actions? = null) =
        navigateTo(
            R.id.action_nav_home_to_task,
            TaskFragment.argumentsBuilder()
                .setAction(action)
                .setTask(task)
                .build()
        )


    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
    }
}