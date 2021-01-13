package aleksandr_logunov.task_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import aleksandr_logunov.task_list.base.BaseActivity
import aleksandr_logunov.task_list.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        with (viewBinding) { // better have this in a base class
            setContentView(root)
            setSupportActionBar(appBarMain.toolbar)
            val navController = findNavController(R.id.nav_host_fragment)
            appBarConfiguration = AppBarConfiguration(
                setOf(R.id.nav_home, R.id.nav_settings),
                drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun addFabOnClick(onClick: View.OnClickListener): Long {
        viewBinding.appBarMain.plusFab.visibility = View.VISIBLE
        viewBinding.appBarMain.plusFab.setOnClickListener(onClick)
        viewBinding.appBarMain.plusFab.tag = Date().time
        return viewBinding.appBarMain.plusFab.tag as Long
    }

    override fun removeFabOnClick(id: Long?) {
        if (viewBinding.appBarMain.plusFab.tag == id) {
            viewBinding.appBarMain.plusFab.visibility = View.GONE
            viewBinding.appBarMain.plusFab.setOnClickListener(null)
            viewBinding.appBarMain.plusFab.tag = null
        }
    }

    override fun showSnack(text: String) {
        Snackbar.make(viewBinding.drawerLayout, text, Snackbar.LENGTH_LONG)
            // TODO: .setAction("Action", null)
            .show()
    }

    override fun setDrawerLockedClose(isLockedClose: Boolean) {
        viewBinding.drawerLayout.setDrawerLockMode(
            if (isLockedClose) DrawerLayout.LOCK_MODE_LOCKED_CLOSED
            else DrawerLayout.LOCK_MODE_UNLOCKED
        )
    }

    override fun navigateTo(destination: Int, args: Bundle?) {
        try {
            findNavController(R.id.nav_host_fragment).navigate(destination, args)
        } catch (e: Exception) {
            Log.w(TAG, "Error while trying to navigate.", e)
        }
    }

    override fun goBack() {
        findNavController(R.id.nav_host_fragment).navigateUp()
    }
}
