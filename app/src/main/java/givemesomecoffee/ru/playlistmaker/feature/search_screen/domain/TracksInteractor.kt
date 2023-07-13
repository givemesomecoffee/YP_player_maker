package givemesomecoffee.ru.playlistmaker.feature.search_screen.domain

import givemesomecoffee.ru.playlistmaker.core.data.local.SearchHistoryStorage
import givemesomecoffee.ru.playlistmaker.core.data.tracks.TracksRepository
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.TrackEntity
import givemesomecoffee.ru.playlistmaker.core.domain.Response
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TrackUi
import kotlinx.coroutines.flow.Flow

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

    fun searchTracks(filter: String): Flow<Response<List<TrackEntity>>> {
        return tracksApi.searchTrack(filter)
    }

    suspend fun getFavouriteTracksIds(): List<String>{
        return tracksApi.getFavouriteTracksId()
    }

}