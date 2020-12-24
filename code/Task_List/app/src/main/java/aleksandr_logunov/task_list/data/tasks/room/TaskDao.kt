package aleksandr_logunov.task_list.data.tasks.room

import androidx.room.*
import aleksandr_logunov.task_list.data.tasks.SimpleTask
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): Flowable<List<Task>>

    @Insert(entity = Task::class, onConflict = OnConflictStrategy.REPLACE)
    fun add(task: SimpleTask): Single<Long>

    @Insert(entity = Task::class, onConflict = OnConflictStrategy.REPLACE)
    fun add(task: Task): Single<Long>

    @Delete
    fun delete(task: Task): Completable

    @Query("SELECT * FROM task WHERE id = :id")
    fun getById(id: Long): Single<Task>
}
