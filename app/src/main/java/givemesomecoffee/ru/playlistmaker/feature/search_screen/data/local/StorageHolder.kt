package givemesomecoffee.ru.playlistmaker.feature.search_screen.data.local

import android.content.Context

object StorageHolder {
    private var INSTANCE: LocalStorageImpl? = null
    fun getSearchHistoryStorage(context: Context): SearchHistoryStorage {
        return INSTANCE ?: synchronized(this) {
            LocalStorageImpl(context).also {
                INSTANCE = it
            }
        }
    }

    fun getSettingsStorage(context: Context): SettingsStorage {
        return INSTANCE ?: synchronized(this) {
            LocalStorageImpl(context).also {
                INSTANCE = it
            }
        }
    }
}