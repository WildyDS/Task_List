package aleksandr_logunov.task_list.base

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding

interface IBaseFragment<T : ViewBinding> {
    var viewBinding: T?
    val isDrawerLockedClose: Boolean
    val fabOnClick: View.OnClickListener?
    fun addFabOnClick(onClick: View.OnClickListener)
    fun removeFabOnClick()
    fun showSnack(text: String)
    fun navigateTo(destination: Int, args: Bundle?)
    fun goBack()
    fun hideKeyboard()
}