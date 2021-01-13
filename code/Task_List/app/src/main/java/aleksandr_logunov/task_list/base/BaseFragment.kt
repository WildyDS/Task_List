package aleksandr_logunov.task_list.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<T : ViewBinding, VM : BaseViewModel?>(@LayoutRes layout: Int) :
    Fragment(layout), IBaseFragment<T> {
    @Suppress("PropertyName")
    val TAG: String? = javaClass.canonicalName

    var viewModel: VM? = null // naming conventions vm

    override val isDrawerLockedClose = false
    override val fabOnClick: View.OnClickListener? = null
    override var viewBinding: T? = null

    var fabOnClickId: Long? = null

    override fun addFabOnClick(onClick: View.OnClickListener) {
        fabOnClickId = (activity as IBaseActivity).addFabOnClick(onClick)
    }

    override fun removeFabOnClick() {
        (activity as IBaseActivity).removeFabOnClick(fabOnClickId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeFabOnClick()
        hideKeyboard()
        viewBinding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as IBaseActivity).setDrawerLockedClose(isDrawerLockedClose)
        if (fabOnClick != null) {
            addFabOnClick(fabOnClick!!)
        }
    }

    override fun showSnack(text: String) {
        (activity as IBaseActivity).showSnack(text)
    }

    override fun hideKeyboard() {
        view?.let {
            (it.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    override fun navigateTo(destination: Int, args: Bundle?) {
        (activity as IBaseActivity).navigateTo(destination, args)
    }

    fun navigateTo(destination: Int) {
        navigateTo(destination, null)
    }

    override fun goBack() {
        hideKeyboard()
        (activity as IBaseActivity).goBack()
    }
}