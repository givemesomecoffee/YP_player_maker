package givemesomecoffee.ru.playlistmaker.core.data.local

interface SettingsStorage {

    fun isDarkTheme(): Boolean

    fun setDarkTheme(isDark: Boolean)

    fun addOnSettingsChangedListener(callback: (Boolean) -> Unit)

    fun removeOnSettingsChangedListener()
}