package aleksandr_logunov.task_list.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import aleksandr_logunov.task_list.R
import aleksandr_logunov.task_list.base.BaseFragment
import aleksandr_logunov.task_list.databinding.FragmentSettingsBinding
import aleksandr_logunov.task_list.dummy.DummyContent

class SettingsFragment :
    BaseFragment<FragmentSettingsBinding, Nothing>(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentSettingsBinding.bind(view)
        for (item in DummyContent.ITEMS) {
            viewBinding!!.rgSettings.addView(RadioButton(context).apply {
                text = getString(R.string.settings_item, item.name, item.details)
                setOnCheckedChangeListener { _, isChecked ->
                    showSnack("Changing settings is not implemented yet.")
                    Log.d(TAG, "${if (isChecked) "Checked" else "Unchecked"}: $item")
                }
            })
        }
    }
}