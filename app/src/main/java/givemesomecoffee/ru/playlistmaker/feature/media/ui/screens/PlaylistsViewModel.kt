package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepository
import givemesomecoffee.ru.playlistmaker.core.data.playlist.Playlist
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val playListRepository: PlayListRepository
): ViewModel() {

    private val _state = MutableLiveData<List<Playlist>?>(null)
    val state: LiveData<List<Playlist>?> = _state

    init {
        viewModelScope.launch {
            playListRepository.trackPlaylists().collectLatest {
                _state.value = it
            }
        }
    }
}