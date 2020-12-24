package aleksandr_logunov.task_list.data.tasks.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import aleksandr_logunov.task_list.data.tasks.ITask

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    override val createdAt: String,

    @ColumnInfo(name = "title", defaultValue = "")
    override var title: String,

    @ColumnInfo(name = "description", defaultValue = "")
    override var description: String
) : ITask {
    @Ignore
    override val savedIn: String = "Database"

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(createdAt)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}
