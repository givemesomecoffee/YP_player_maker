package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.add_to_playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepository
import givemesomecoffee.ru.playlistmaker.core.data.playlist.Playlist
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddToPlaylistViewModel(
    private val playlistsRepository: PlayListRepository
) : ViewModel() {

    private lateinit var trackId: String
    fun setTrackId(id: String) {
        trackId = id
    }

    private val _state = MutableLiveData<List<Playlist>?>(null)
    val state: LiveData<List<Playlist>?> = _state

    private val _event = MutableLiveData<AddToPlayListEvents?>(null)
    val event: LiveData<AddToPlayListEvents?> = _event


    fun addToPlayList(playlist: Playlist) {
        viewModelScope.launch {
            if (trackId in playlist.tracks) {
                _event.value = AddToPlayListEvents.AlreadyAddedToPlaylist(playlist.name)
            } else {
                playlistsRepository.addTrack(trackId, playlist)
                _event.value = AddToPlayListEvents.AddedToPlayList(playlist.name)
            }
        }
    }

    fun onEventDone(){
        _event.value = null
    }


    init {
        viewModelScope.launch {
            playlistsRepository.trackPlaylists().collectLatest {
                _state.value = it
            }
        }
    }
}

