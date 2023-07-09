package givemesomecoffee.ru.playlistmaker.core.data.local

import android.content.SharedPreferences
import com.google.gson.Gson
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TrackUi

class LocalStorageImpl(private val storage: SharedPreferences) : SearchHistoryStorage,
    SettingsStorage {

    private var globalSettingsListener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    override fun getSearchHistory(): Array<TrackUi> {
        val json = storage.getString(SEARCH_HISTORY_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<TrackUi>::class.java)
    }

    override fun updateSearchHistory(track: TrackUi) {
        val cache = getSearchHistory().toMutableList()
        cache.firstOrNull { it.trackId == track.trackId }?.let { cache.remove(it) }
        if (cache.size == 10) {
            cache.removeAt(cache.lastIndex)
        }
        cache.add(0, track)
        val json = Gson().toJson(cache)
        storage.edit()
            ?.putString(SEARCH_HISTORY_KEY, json)
            ?.apply()
    }

    override fun clearSearchHistory() {
        storage.edit()
            ?.putString(SEARCH_HISTORY_KEY, null)
            ?.apply()
    }

    override fun isDarkTheme(): Boolean {
        return storage.getBoolean(THEME_KEY, false)
    }

    override fun setDarkTheme(isDark: Boolean) {
        storage.edit()?.putBoolean(THEME_KEY, isDark)?.apply()
    }

    override fun addOnSettingsChangedListener(callback: (Boolean) -> Unit) {
        globalSettingsListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == THEME_KEY) {
                    callback(isDarkTheme())
                }
            }
        storage.registerOnSharedPreferenceChangeListener(globalSettingsListener)
    }

    override fun removeOnSettingsChangedListener() {
        storage.unregisterOnSharedPreferenceChangeListener(globalSettingsListener)
    }

    companion object {
        const val SEARCH_HISTORY_KEY = "search_history"
        const val THEME_KEY = "isDarkTheme"
    }

}
