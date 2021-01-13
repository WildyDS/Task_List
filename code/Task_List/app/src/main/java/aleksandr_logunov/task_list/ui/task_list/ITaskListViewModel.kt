package aleksandr_logunov.task_list.ui.task_list

import androidx.lifecycle.LiveData
import aleksandr_logunov.task_list.data.tasks.ITask

typealias OnDelete = () -> Unit

interface ITaskListViewModel {
    val tabs: LiveData<List<String>>
    val tasks: LiveData<List<ITask>>
    fun loadTasks()
    fun filterTasks(by: String?)
    fun saveTask(task: ITask)
    fun deleteTask(task: ITask, onDelete: OnDelete? = null)
}