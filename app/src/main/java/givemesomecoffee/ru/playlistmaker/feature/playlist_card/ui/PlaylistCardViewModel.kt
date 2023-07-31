package givemesomecoffee.ru.playlistmaker.feature.playlist_card.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist
import givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain.DeletePlaylistUseCase
import givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain.DeleteTrackUseCase
import givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain.GetPlaylistUseCase
import givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain.GetTracksUseCase
import givemesomecoffee.ru.playlistmaker.feature.search_screen.domain.TracksInteractor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PlaylistCardViewModel(
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val getTracksUseCase: GetTracksUseCase,
    private val deleteTrackUseCase: DeleteTrackUseCase,
    private val deletePlaylistUseCase: DeletePlaylistUseCase,
    private val tracksInteractor: TracksInteractor
) : ViewModel() {

    private val _state = MutableLiveData<PlaylistUi?>(null)
    val state: LiveData<PlaylistUi?> = _state

    private var id: Long? = null

    fun sync(id: Long) {
        this.id = id
        viewModelScope.launch {
            getPlaylistUseCase.invoke(id).collectLatest {
                val tracks = getTracksUseCase.invoke(it.tracks)
                val favourites = tracksInteractor.getFavouriteTracksIds()
                _state.value = PlaylistUi.mapFrom(
                    it,
                    tracks.map { it.toDomain().copy(isFavourite = it.trackId in favourites) })
            }
        }
    }

    fun deleteTrack(trackId: String, id: Long) {
        viewModelScope.launch {
            deleteTrackUseCase.invoke(trackId, id)
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            deletePlaylistUseCase.invoke(playlist)
        }
    }
}