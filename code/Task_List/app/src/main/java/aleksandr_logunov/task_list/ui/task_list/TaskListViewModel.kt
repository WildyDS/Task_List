package aleksandr_logunov.task_list.ui.task_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import aleksandr_logunov.task_list.Injector
import aleksandr_logunov.task_list.base.BaseViewModel
import aleksandr_logunov.task_list.data.tasks.ITask
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class TaskListViewModel : BaseViewModel(), ITaskListViewModel {
    private val _tasks = MutableLiveData<List<ITask>>()

    override val tasks: LiveData<List<ITask>> = _tasks

    override fun loadTasks() {
        Injector.tasksRepository.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onBackpressureDrop()
            .subscribe { tasks -> _tasks.postValue(tasks) }
            .run { addDisposable(this) }
    }

    private val _tabs = MutableLiveData<List<String>>().apply {
        value = tabList
    }
    override val tabs: LiveData<List<String>> = _tabs

    override fun filterTasks(by: String?) {
        TODO("Not yet implemented")
    }

    override fun saveTask(task: ITask) {
        TODO("Not yet implemented")
    }

    override fun deleteTask(task: ITask, onDelete: Runnable?) { // why do u need a runnable? better use coroutines or RxJava, u can pass functions as func : ():Unit
        Injector.tasksRepository.delete(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { }
            .subscribe(
                { onDelete?.run() },
                { error -> Log.w(TAG, "Delete error! ", error) }
            )
            .run { addDisposable(this) }
    }

    companion object {
        // TODO: Save tabs somewhere else - good idea, like a sealed class or an enum
        private const val tabAll = "All"
        private const val tabToDo = "To Do"
        private const val tabInProgress = "In Progress"
        private const val tabDone = "Done"
        val tabList = arrayListOf(tabAll, tabToDo, tabInProgress, tabDone, tabToDo, tabInProgress, tabDone, tabToDo, tabInProgress, tabDone, tabToDo, tabInProgress, tabDone, tabToDo, tabInProgress, tabDone)
    }
}