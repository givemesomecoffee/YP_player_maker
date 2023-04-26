package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import givemesomecoffee.ru.playlistmaker.feature.track_card.domain.TrackInteractor
import givemesomecoffee.ru.playlistmaker.feature.track_card.model.TrackCardScreenState
import givemesomecoffee.ru.playlistmaker.feature.track_card.model.TrackUi

class TrackCardViewModel(private val application: Application) : AndroidViewModel(application) {

    private val trackInteractor = TrackInteractor()

    private val _state = MutableLiveData(TrackCardScreenState(loading = true, data = null))
    val state: LiveData<TrackCardScreenState> = _state
    fun sync(id: String) {
        val t = Thread {
            trackInteractor.getTrack(id).content?.let {
                _state.postValue(
                    TrackCardScreenState(
                        data = TrackUi.mapFrom(
                            it,
                            application
                        ), loading = false
                    )
                )
            }
        }
        t.start()
    }
}