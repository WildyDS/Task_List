package aleksandr_logunov.task_list

import android.app.Application

@Suppress("unused")
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.initWithContext(this)
    }
}