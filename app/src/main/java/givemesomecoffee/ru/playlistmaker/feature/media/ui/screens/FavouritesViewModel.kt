package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TrackUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val favouriteTracksInteractor: FavouriteTracksInteractor
) : ViewModel() {

    private var trackJob: Job? = null
    private val _state = MutableStateFlow<List<TrackUi>?>(null)
    val state: Flow<List<TrackUi>?>
        get() = _state.onSubscription {
            trackFavourites()
        }.onCompletion {
            if (_state.subscriptionCount.value == 0) {
                trackJob?.cancel()
            }
        }

    private fun trackFavourites() {
        trackJob = viewModelScope.launch {
            favouriteTracksInteractor.getFavouriteTracks().collectLatest {
                Log.d("custom", it.toString())
                _state.value = it.map {
                    TrackUi.mapFrom(it)
                }
            }
        }
    }
}
