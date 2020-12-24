package aleksandr_logunov.task_list.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single


interface IRepository<T> {
    fun getAll(): Flowable<List<T>>
    fun add(value: T): Single<T>
    fun delete(value: T): Completable
    fun getById(id: Long): Single<T>
}
