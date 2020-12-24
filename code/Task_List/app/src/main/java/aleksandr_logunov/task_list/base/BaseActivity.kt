package aleksandr_logunov.task_list.base

import androidx.appcompat.app.AppCompatActivity
import aleksandr_logunov.task_list.databinding.ActivityMainBinding

abstract class BaseActivity<VM> : AppCompatActivity(), IBaseActivity {
    lateinit var viewBinding: ActivityMainBinding

    @Suppress("PropertyName")
    val TAG: String? = javaClass.canonicalName
}