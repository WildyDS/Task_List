package aleksandr_logunov.task_list.ui.task

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import aleksandr_logunov.task_list.R
import aleksandr_logunov.task_list.base.BaseFragment
import aleksandr_logunov.task_list.data.tasks.ITask
import aleksandr_logunov.task_list.databinding.FragmentTaskBinding
import aleksandr_logunov.task_list.extensions.ellipsize

class TaskFragment : BaseFragment<FragmentTaskBinding, TaskViewModel>(R.layout.fragment_task) {
    override val isDrawerLockedClose = true
    private var action = Actions.View
    private var initialTask: ITask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            action = it.getSerializable(ARG_ACTION) as Actions
            initialTask = it.getParcelable(ARG_TASK) as ITask?
        }
        savedInstanceState?.let {
            initialTask = it.getParcelable(ARG_TASK) as ITask?
        }
        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentTaskBinding.bind(view)
        viewModel?.let { vm ->
            initialTask?.let { vm.setTask(it) }
            with(viewBinding!!) {
                vm.title.observe(viewLifecycleOwner, {
                    etTaskTitle.setText(it)
                })
                vm.description.observe(viewLifecycleOwner, {
                    etTaskDescription.setText(it)
                })
                taskSave.setOnClickListener {
                    vm.save(etTaskTitle.text.toString(), etTaskDescription.text.toString())
                    { task ->
                        showSnack(getString(R.string.task_saved, task.title.ellipsize(20)))
                        goBack()
                    }
                }
                taskCancel.setOnClickListener { goBack() }
                taskDelete.setOnClickListener {
                    vm.delete {
                        showSnack(
                            getString(
                                R.string.task_deleted,
                                vm.title.value?.ellipsize(20)
                            )
                        )
                        goBack()
                    }
                }
                taskSave.isVisible = action != Actions.View
                taskCancel.isVisible = action != Actions.View
                taskDelete.isVisible = action == Actions.Edit
                etTaskTitle.isFocusable = action != Actions.View
                etTaskDescription.isFocusable = action != Actions.View
            }
        }
    }

    companion object {
        const val ARG_ACTION = "action"
        const val ARG_TASK = "initial_task"

        enum class Actions {
            View,
            Edit,
            Create,
        }

        fun argumentsBuilder() = ArgumentsBuilder()

        class ArgumentsBuilder {
            private var action: Actions? = Actions.View
            private var task: ITask? = null

            fun setAction(action: Actions?): ArgumentsBuilder {
                if (action != null) {
                    this.action = action
                }
                return this
            }

            fun setTask(task: ITask?): ArgumentsBuilder {
                this.task = task
                return this
            }

            fun build(): Bundle {
                return Bundle().apply {
                    putSerializable(ARG_ACTION, action)
                    putParcelable(ARG_TASK, task)
                }
            }
        }
    }
}