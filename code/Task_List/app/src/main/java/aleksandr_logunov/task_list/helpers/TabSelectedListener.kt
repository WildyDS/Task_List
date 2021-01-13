package aleksandr_logunov.task_list.helpers

import com.google.android.material.tabs.TabLayout

interface TabSelectedListener : TabLayout.OnTabSelectedListener {
    override fun onTabUnselected(tab: TabLayout.Tab?) {} // no need in interfaces {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}
}