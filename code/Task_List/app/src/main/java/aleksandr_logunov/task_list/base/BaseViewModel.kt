package aleksandr_logunov.task_list.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel : ViewModel(), IBaseViewModel {
    @Suppress("PropertyName")
    val TAG: String? = javaClass.canonicalName
    private val mDisposable = CompositeDisposable()

    override fun addDisposable(disposable: Disposable) = mDisposable.add(disposable)

    override fun addAllDisposables(vararg disposables: Disposable) = mDisposable.addAll(*disposables)

    override fun clearDisposable() {
        mDisposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        mDisposable.clear()
    }
}