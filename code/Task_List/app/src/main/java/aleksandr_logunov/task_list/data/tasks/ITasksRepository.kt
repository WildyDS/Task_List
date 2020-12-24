package aleksandr_logunov.task_list.data.tasks

import aleksandr_logunov.task_list.data.IRepository
import io.reactivex.rxjava3.core.Single

interface ITasksRepository<T : ITask> : IRepository<T> {
    fun add(title: String, description: String): Single<T>
}