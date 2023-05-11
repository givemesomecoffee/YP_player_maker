package givemesomecoffee.ru.playlistmaker.core.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TrackUi

class LocalStorageImpl(context: Context) : SearchHistoryStorage, SettingsStorage {

    private var storage: SharedPreferences? = null

    private var globalSettingsListener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    init {
        storage = context.getSharedPreferences(
            PRACTICUM_EXAMPLE_PREFERENCES,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    override fun getSearchHistory(): Array<TrackUi> {
        val json = storage?.getString(SEARCH_HISTORY_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<TrackUi>::class.java)
    }

    override fun updateSearchHistory(track: TrackUi) {
        val cache = getSearchHistory().toMutableList()
        cache.firstOrNull { it.trackId == track.trackId }?.let { cache.remove(it) }
        if (cache.size == 10) {
            cache.removeAt(cache.lastIndex)
        }
        cache.add(0, track.copy(isSearchResult = false))
        val json = Gson().toJson(cache)
        storage?.edit()
            ?.putString(SEARCH_HISTORY_KEY, json)
            ?.apply()
    }

    override fun clearSearchHistory() {
        storage?.edit()
            ?.putString(SEARCH_HISTORY_KEY, null)
            ?.apply()
    }

    override fun isDarkTheme(): Boolean {
        return storage?.getBoolean(THEME_KEY, false) ?: false
    }

    override fun setDarkTheme(isDark: Boolean) {
        storage?.edit()?.putBoolean(THEME_KEY, isDark)?.apply()
    }

    override fun addOnSettingsChangedListener(callback: (Boolean) -> Unit) {
        globalSettingsListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == THEME_KEY) {
                    callback(isDarkTheme())
                }
            }
        storage?.registerOnSharedPreferenceChangeListener(globalSettingsListener)
    }

    override fun removeOnSettingsChangedListener() {
        storage?.unregisterOnSharedPreferenceChangeListener(globalSettingsListener)
    }

    companion object {
        const val PRACTICUM_EXAMPLE_PREFERENCES = "practicum_example_preferences"
        const val SEARCH_HISTORY_KEY = "search_history"
        const val THEME_KEY = "isDarkTheme"
    }

}
