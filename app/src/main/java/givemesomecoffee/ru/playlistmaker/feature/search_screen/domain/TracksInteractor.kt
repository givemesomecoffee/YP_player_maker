package givemesomecoffee.ru.playlistmaker.feature.search_screen.domain

import givemesomecoffee.ru.playlistmaker.core.data.local.LocalTracksStorage
import givemesomecoffee.ru.playlistmaker.core.data.local.SearchHistoryStorage
import givemesomecoffee.ru.playlistmaker.core.data.tracks.TracksRepository
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.core.domain.Response
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TrackUi

class TracksInteractor(
    private val localStorage: SearchHistoryStorage,
    private val tracksApi: TracksRepository
) {

    fun updateSearchHistory(track: TrackUi) {
        localStorage.updateSearchHistory(track)
    }

    fun clearSearchHistory() {
        localStorage.clearSearchHistory()
    }

    fun getSearchHistory(): List<TrackUi> {
        return localStorage.getSearchHistory().toList()
    }

    fun searchTracks(filter: String): Response<List<Track>> {
        return tracksApi.searchTrack(filter).also {
            it.content?.let { LocalTracksStorage.tracks = it }
        }
    }

}