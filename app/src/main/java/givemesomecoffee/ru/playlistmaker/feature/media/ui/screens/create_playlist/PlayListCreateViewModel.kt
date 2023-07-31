package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.create_playlist

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.PlayListModel
import givemesomecoffee.ru.playlistmaker.core.navigation.Screens
import givemesomecoffee.ru.playlistmaker.feature.media.domain.CreatePlayListUseCase
import givemesomecoffee.ru.playlistmaker.feature.media.domain.GetPlaylistUseCase
import kotlinx.coroutines.launch

class PlayListCreateViewModel(
    private val createPlayListUseCase: CreatePlayListUseCase,
    private val getPlaylistUseCase: GetPlaylistUseCase
) : ViewModel() {

    private val _state = MutableLiveData(PlayListModel(name = ""))
    val state: LiveData<PlayListModel> = _state

    private val _event = MutableLiveData<CreatePlayListEvents>(null)
    val event: LiveData<CreatePlayListEvents?> = _event

    private lateinit var args: Screens.EditPlayListArgs

    fun obtainArguments(args: Screens.EditPlayListArgs){
        this.args = args
        if(args is Screens.EditPlayListArgs.Edit){
            viewModelScope.launch {
                getPlaylistUseCase.invoke(id = args.id).let {
                    Log.d("custom", it.toString())
                   _state.value =  PlayListModel(
                        name = it.name,
                        id = it.id,
                        path = it.path,
                        tracks = it.tracks,
                        size = it.size,
                        description = it.description
                    )
                }
            }
        }
    }

    fun updateName(text: String) {
        if (text != _state.value?.name) {
            _state.value = _state.value?.copy(name = text)
        }
    }

    fun updateDescription(text: String) {
        if (text != _state.value?.description) {
            _state.value = _state.value?.copy(description = text)
        }
    }

    fun onImageSelected(uri: Uri) {
        _state.value = _state.value?.copy(path = uri.toString())
    }

    fun createPlayList(localPath: String?) {
        viewModelScope.launch {
            state.value?.let { playlist ->
                createPlayListUseCase.invoke(playlist.copy(path = localPath))
                if(args is Screens.EditPlayListArgs.Create) {
                    _event.value = CreatePlayListEvents.Created(playlist.name)
                } else {
                    _event.value = CreatePlayListEvents.Edited
                }
            }
        }
    }
}