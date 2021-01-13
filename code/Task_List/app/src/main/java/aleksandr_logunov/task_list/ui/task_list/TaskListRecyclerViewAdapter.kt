package aleksandr_logunov.task_list.ui.task_list

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.ActionMenuView
import androidx.recyclerview.widget.RecyclerView
import aleksandr_logunov.task_list.R
import aleksandr_logunov.task_list.data.tasks.ITask

/**
 * [RecyclerView.Adapter] that can display a [ITask].
 */
class TaskListRecyclerViewAdapter(
    private val tasks: MutableList<ITask>,
    private val onTaskPress: OnTaskPress?,
) : RecyclerView.Adapter<TaskListRecyclerViewAdapter.ViewHolder>() {

    fun replaceAll(newTasks: List<ITask>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_list_item, parent, false)
        val menuBuilder = view.findViewById<ActionMenuView>(R.id.amv_task).menu
        MenuInflater(parent.context).inflate(R.menu.task_list_item_menu, menuBuilder)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tasks[position]
        holder.taskTitleTV.text = item.title
        holder.taskDescriptionTV.text = item.description
        if (onTaskPress != null) {
            holder.rootView.setOnClickListener {
                onTaskPress.onPress(item, position)
            }
        }
        holder.taskAMV.setOnMenuItemClickListener { menuItem ->
            if (onTaskPress == null) {
                false
            } else {
                onTaskPress.onActionPress(item, position, menuItem.itemId)
                true
            }
        }
    }

    override fun getItemCount(): Int = tasks.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView: View = view.rootView
        val taskTitleTV: TextView = view.findViewById(R.id.tv_task_title)
        val taskDescriptionTV: TextView = view.findViewById(R.id.tv_task_description)
        val taskAMV: ActionMenuView = view.findViewById(R.id.amv_task)
        override fun toString(): String {
            return "${super.toString()} '${taskTitleTV.text}'"
        }
    }

    interface OnTaskPress {
        fun onPress(task: ITask, position: Int)
        fun onActionPress(task: ITask, position: Int, action: Int)
    }

    companion object {
        const val TAG = "TaskListRVAdapter"
    }
}