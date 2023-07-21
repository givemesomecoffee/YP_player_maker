package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.create_playlist

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListModel
import kotlinx.coroutines.launch

class PlayListCreateViewModel(
    private val createPlayListUseCase: CreatePlayListUseCase
) : ViewModel() {

    private val _state = MutableLiveData<PlayListModel>(PlayListModel(name = ""))
    val state: LiveData<PlayListModel> = _state

    private val _event = MutableLiveData<CreatePlayListEvents>(null)
    val event: LiveData<CreatePlayListEvents?> = _event

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
                _event.value = CreatePlayListEvents.Created(playlist.name)
            }
        }
    }

}