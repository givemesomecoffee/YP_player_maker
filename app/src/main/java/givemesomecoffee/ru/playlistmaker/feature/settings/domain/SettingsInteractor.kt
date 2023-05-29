package givemesomecoffee.ru.playlistmaker.feature.settings.domain

import givemesomecoffee.ru.playlistmaker.core.data.local.SettingsStorage

class SettingsInteractor(private val settingsStorage: SettingsStorage) {

    fun isDarkTheme(): Boolean = settingsStorage.isDarkTheme()

    fun setDarkTheme(isDark: Boolean) {
        settingsStorage.setDarkTheme(isDark)
    }
}