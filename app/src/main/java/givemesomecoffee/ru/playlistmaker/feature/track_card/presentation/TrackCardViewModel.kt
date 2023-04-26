package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import givemesomecoffee.ru.playlistmaker.core.di.ServiceLocator
import givemesomecoffee.ru.playlistmaker.feature.track_card.domain.TrackInteractor
import givemesomecoffee.ru.playlistmaker.feature.track_card.model.TrackCardScreenState
import givemesomecoffee.ru.playlistmaker.feature.track_card.model.TrackUi
import givemesomecoffee.ru.playlistmaker.core.presentation.player.PlayerHolder
import kotlinx.coroutines.flow.*

class TrackCardViewModel(
    private val application: Application,
    private val playerHolder: PlayerHolder,
    private val trackInteractor: TrackInteractor
) : AndroidViewModel(application), PlayerHolder by playerHolder {
    private val track = MutableStateFlow<TrackUi?>(null)
    val state: StateFlow<TrackCardScreenState> =
        combine(track.filterNotNull(), playerHolder.playerState) { track, player ->
            TrackCardScreenState(false, track, player)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            TrackCardScreenState(loading = true, data = null)
        )

    fun sync(id: String) {
        val t = Thread {
            trackInteractor.getTrack(id).content?.let {
                track.value = TrackUi.mapFrom(it, application)
            }
        }
        t.start()
    }

    companion object {
        fun getViewModelFactory(application: Application): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val interactor = TrackInteractor()
                val playerHolder = ServiceLocator.resolvePlayerHolder()
                TrackCardViewModel(
                    application, playerHolder, interactor
                )
            }
        }
    }
}