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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PlaylistCardViewModel(
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val getTracksUseCase: GetTracksUseCase,
    private val deleteTrackUseCase: DeleteTrackUseCase,
    private val deletePlaylistUseCase: DeletePlaylistUseCase
) : ViewModel() {

    private val _state = MutableLiveData<PlaylistUi?>(null)
    val state: LiveData<PlaylistUi?> = _state

    private var id: String? = null

    fun sync(id: String) {
        this.id = id
        viewModelScope.launch {
            getPlaylistUseCase.invoke(id).collectLatest {
                val tracks = getTracksUseCase.invoke(it.tracks)
                _state.value = PlaylistUi.mapFrom(it, tracks)
            }
        }
    }

    fun deleteTrack(trackId: String, id: String) {
        viewModelScope.launch {
            deleteTrackUseCase.invoke(trackId, id)
        }
    }

    fun deletePlaylist(playlist: Playlist){
        viewModelScope.launch {
            deletePlaylistUseCase.invoke(playlist)
        }
    }
}