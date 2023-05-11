package givemesomecoffee.ru.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import givemesomecoffee.ru.playlistmaker.core.data.local.SettingsStorage
import givemesomecoffee.ru.playlistmaker.core.data.local.StorageHolder

class App : Application() {
    var darkTheme = false

    private var localStorage: SettingsStorage? = null

    override fun onCreate() {
        super.onCreate()
        localStorage = StorageHolder.getSettingsStorage(this)
        darkTheme = localStorage?.isDarkTheme() == true
        switchTheme()
        localStorage?.addOnSettingsChangedListener() {
            darkTheme = it
            switchTheme()
        }
    }

    override fun onTerminate() {
        localStorage?.removeOnSettingsChangedListener()
        super.onTerminate()
    }

    private fun switchTheme() {
        AppCompatDelegate.setDefaultNightMode(
            if (darkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}