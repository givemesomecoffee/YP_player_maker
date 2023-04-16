package givemesomecoffee.ru.playlistmaker.data.local

import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TrackUi

interface SearchHistoryStorage {
    fun getSearchHistory(): Array<TrackUi>
    fun updateSearchHistory(track: TrackUi)
    fun clearSearchHistory()
}