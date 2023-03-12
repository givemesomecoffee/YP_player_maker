package givemesomecoffee.ru.playlistmaker.feature.search_screen.data.local

interface SettingsStorage {

    fun isDarkTheme(): Boolean

    fun setDarkTheme(isDark: Boolean)

    fun addOnSettingsChangedListener(callback: (Boolean) -> Unit)

    fun removeOnSettingsChangedListener()
}