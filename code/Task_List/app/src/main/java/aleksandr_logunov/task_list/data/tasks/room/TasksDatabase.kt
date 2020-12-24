package aleksandr_logunov.task_list.data.tasks.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}