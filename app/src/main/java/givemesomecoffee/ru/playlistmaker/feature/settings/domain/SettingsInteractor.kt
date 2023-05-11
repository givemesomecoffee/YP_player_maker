package givemesomecoffee.ru.playlistmaker.feature.settings.domain

import android.app.Application
import givemesomecoffee.ru.playlistmaker.core.data.local.SettingsStorage
import givemesomecoffee.ru.playlistmaker.core.data.local.StorageHolder

class SettingsInteractor(application: Application) {

    private var settingsStorage: SettingsStorage =
        StorageHolder.getSettingsStorage(application)

    fun isDarkTheme(): Boolean = settingsStorage.isDarkTheme()

    fun setDarkTheme(isDark: Boolean) {
        settingsStorage.setDarkTheme(isDark)
    }

    companion object{
        fun create(application: Application): SettingsInteractor = SettingsInteractor(application)
    }
}