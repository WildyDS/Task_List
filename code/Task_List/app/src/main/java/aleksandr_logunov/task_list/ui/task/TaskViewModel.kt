package aleksandr_logunov.task_list.ui.task

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import aleksandr_logunov.task_list.Injector
import aleksandr_logunov.task_list.base.BaseViewModel
import aleksandr_logunov.task_list.data.tasks.ITask
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class TaskViewModel : BaseViewModel(), ITaskViewModel {
    override val title: LiveData<String> = MutableLiveData()
    override val titleError: LiveData<String> = MutableLiveData()
    override val description: LiveData<String> = MutableLiveData()
    override val descriptionError: LiveData<String> = MutableLiveData()

    private var _task: ITask? = null

    override fun save(title: String, description: String, onSaved: OnSaved?) {
        // Using .let for storing _task in "it" variable cause it can be changed in runtime
        _task.let {
            (
                    // So we can check it for null
                    if (it == null)
                        // And create new task
                        Injector.tasksRepository.add(title, description)
                    else
                        // Or edit current
                        Injector.tasksRepository.add(
                            it.run {
                                it.title = title
                                it.description = description
                                it
                            }
                        )
                    )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { }
                .subscribe(
                    { task ->
                        setTask(task)
                        onSaved?.invoke(task)
                    },
                    { error -> Log.w(TAG, "Save error! ", error) }
                )
                .run { addDisposable(this) }
        }

    }

    override fun delete(onDeleted: Runnable?) {
        _task?.let { task ->
            Injector.tasksRepository.delete(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { }
                .subscribe(
                    { onDeleted?.run() },
                    { error -> Log.e(TAG, "Delete error! ", error) }
                )
                .run { addDisposable(this) }
        }
    }

    override fun setTask(task: ITask) {
        _task = task
        (title as MutableLiveData).value = _task!!.title
        (description as MutableLiveData).value = _task!!.description
    }

    override fun getTask(): ITask? = _task

    override fun validateInput() {
        TODO("Not yet implemented")
    }
}