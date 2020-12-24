package aleksandr_logunov.task_list.base

import android.os.Bundle
import android.view.View

interface IBaseActivity {
    fun addFabOnClick(onClick: View.OnClickListener): Long?
    fun removeFabOnClick(id: Long?)

    fun showSnack(text: String)

    // Navigation
    fun navigateTo(destination: Int, args: Bundle?)
    fun setDrawerLockedClose(isLockedClose: Boolean)
    fun goBack()

}