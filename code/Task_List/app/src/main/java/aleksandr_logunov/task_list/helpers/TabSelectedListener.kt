package aleksandr_logunov.task_list.helpers

import com.google.android.material.tabs.TabLayout

// Helper interface to make these two empty interfaces optional
interface TabSelectedListener : TabLayout.OnTabSelectedListener {
    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}
}