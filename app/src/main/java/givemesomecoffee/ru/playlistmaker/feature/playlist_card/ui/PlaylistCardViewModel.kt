package givemesomecoffee.ru.playlistmaker.feature.playlist_card.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain.GetPlaylistUseCase
import givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain.GetTracksUseCase
import kotlinx.coroutines.launch

class PlaylistCardViewModel(
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val getTracksUseCase: GetTracksUseCase
): ViewModel() {

    private val _state = MutableLiveData<PlaylistUi?>(null)
    val state: LiveData<PlaylistUi?> = _state

    fun sync(id: String){
        viewModelScope.launch{
            val playlist = getPlaylistUseCase.invoke(id)
            val tracks = getTracksUseCase.invoke(playlist.tracks)
            Log.d("custom", tracks.toString())
           _state.value = PlaylistUi.mapFrom(playlist, tracks)
        }
    }
}