package aleksandr_logunov.task_list.base

import io.reactivex.rxjava3.disposables.Disposable

interface IBaseViewModel {
    fun addDisposable(d: Disposable): Boolean
    fun addAllDisposables(vararg ds: Disposable): Boolean
    fun clearDisposable()
}