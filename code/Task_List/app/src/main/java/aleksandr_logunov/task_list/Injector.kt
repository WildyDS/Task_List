package aleksandr_logunov.task_list

import android.content.Context
import android.util.Log
import aleksandr_logunov.task_list.data.tasks.ITask
import aleksandr_logunov.task_list.data.tasks.ITasksRepository
import aleksandr_logunov.task_list.data.tasks.room.TasksRepository

class Injector {
    companion object {
        private const val TAG = "Injector"

        lateinit var tasksRepository: ITasksRepository<ITask>

        fun initWithContext(context: Context) {
            tasksRepository = TasksRepository.initWithContext(context) as ITasksRepository<ITask>
            Log.d(TAG, "Initialized")
        }
    }
}
