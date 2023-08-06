package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import givemesomecoffee.ru.playlistmaker.core.data.favourites.FavouriteTracksRepository
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.core.presentation.player.PlayerHolder
import givemesomecoffee.ru.playlistmaker.feature.track_card.domain.TrackInteractor
import givemesomecoffee.ru.playlistmaker.feature.track_card.model.TrackCardScreenState
import givemesomecoffee.ru.playlistmaker.feature.track_card.model.TrackUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrackCardViewModel(
    private val playerHolder: PlayerHolder,
    private val trackInteractor: TrackInteractor,
    private val repo: FavouriteTracksRepository
) : ViewModel(), PlayerHolder by playerHolder {
    private val track1 = MutableStateFlow<Track?>(null)

    val state: StateFlow<TrackCardScreenState> =
        combine(track1.filterNotNull(), playerHolder.playerState) { track, player ->
            TrackCardScreenState(false, TrackUi.mapFrom(track, ::toggleFavouriteTrack), player)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            TrackCardScreenState(loading = true, data = null)
        )

    fun sync(id: String, isFavourite: Boolean) {
        val t = Thread {
            trackInteractor.getTrack(id).content?.let {
                track1.value = it.toDomain().copy(isFavourite = isFavourite)
            }
        }
        t.start()
    }

    private fun toggleFavouriteTrack(track: Track) {
        viewModelScope.launch {
            if (track.isFavourite) {
                repo.removeFromFavourites(track.trackId)
            } else {
                repo.addToFavourites(track)
            }

            track1.value = track1.value?.toDomain()?.copy(isFavourite = !track.isFavourite)
        }
    }

    override fun onCleared() {
        release()
        super.onCleared()
    }
}