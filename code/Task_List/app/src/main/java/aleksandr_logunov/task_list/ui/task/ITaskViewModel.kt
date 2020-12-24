package aleksandr_logunov.task_list.ui.task

import androidx.lifecycle.LiveData
import aleksandr_logunov.task_list.data.tasks.ITask

typealias OnSaved = (ITask) -> Unit

interface ITaskViewModel {
    val title: LiveData<String>
    val titleError: LiveData<String>
    val description: LiveData<String>
    val descriptionError: LiveData<String>
    fun save(title: String, description: String, onSaved: OnSaved? = null)
    fun delete(onDeleted: Runnable? = null)
    fun setTask(task: ITask)
    fun getTask(): ITask?
    fun validateInput()
}
