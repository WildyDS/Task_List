package aleksandr_logunov.task_list.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel(), IBaseViewModel {
    @Suppress("PropertyName")
    val TAG: String? = javaClass.canonicalName
    private val disposable = CompositeDisposable()

    override fun addDisposable(d: Disposable) = disposable.add(d)

    override fun addAllDisposables(vararg ds: Disposable) = disposable.addAll(*ds)

    override fun clearDisposable() {
        disposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}