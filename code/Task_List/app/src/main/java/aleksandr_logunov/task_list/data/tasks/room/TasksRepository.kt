package aleksandr_logunov.task_list.data.tasks.room

import android.content.Context
import androidx.room.Room
import aleksandr_logunov.task_list.data.tasks.ITasksRepository
import aleksandr_logunov.task_list.data.tasks.SimpleTask
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class TasksRepository(ctx: Context) : ITasksRepository<Task> {
    private var db: TasksDatabase = Room.databaseBuilder(
        ctx,
        TasksDatabase::class.java,
        "tasks-database"
    ).build()

    override fun getAll(): Flowable<List<Task>> = db.taskDao().getAll()

    override fun add(value: Task): Single<Task> = db.taskDao()
        .add(value)
        .flatMap { db.taskDao().getById(it) }

    override fun add(title: String, description: String): Single<Task> = db.taskDao()
        .add(SimpleTask(title, description))
        .flatMap { db.taskDao().getById(it) }

    override fun delete(value: Task): Completable = db.taskDao().delete(value)

    companion object {
        private var selfInstance: ITasksRepository<Task>? = null

        fun initWithContext(ctx: Context): ITasksRepository<Task> {
            if (selfInstance == null) {
                selfInstance = TasksRepository(ctx.applicationContext)
            } else {
                throw InstantiationException("Already initialized.")
            }
            return selfInstance!!
        }

        fun getInstance(): ITasksRepository<Task>? {
            if (selfInstance == null) {
                throw UninitializedPropertyAccessException("Should init repository first.")
            }
            return selfInstance!!
        }
    }

    override fun getById(id: Long): Single<Task> = db.taskDao().getById(id)
}
