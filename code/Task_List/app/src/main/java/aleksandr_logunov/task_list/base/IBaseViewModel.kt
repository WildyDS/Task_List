package aleksandr_logunov.task_list.base

import io.reactivex.rxjava3.disposables.Disposable

interface IBaseViewModel {
    fun addDisposable(disposable: Disposable): Boolean
    fun addAllDisposables(vararg disposables: Disposable): Boolean
    fun clearDisposable()
}