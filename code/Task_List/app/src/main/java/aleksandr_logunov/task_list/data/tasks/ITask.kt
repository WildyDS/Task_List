package aleksandr_logunov.task_list.data.tasks

import android.os.Parcelable

interface ITask : Parcelable {
    val createdAt: String?
    var title: String
    var description: String
    val savedIn: String?
}
