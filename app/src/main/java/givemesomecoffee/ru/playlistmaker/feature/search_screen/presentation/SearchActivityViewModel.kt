package givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import givemesomecoffee.ru.playlistmaker.feature.search_screen.domain.TracksInteractor
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.SearchScreenUi
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TrackUi

class SearchActivityViewModel(private val tracksInteractor: TracksInteractor) :
    ViewModel() {

    private val searchRunnable = Runnable { search() }
    private val handler = Handler(Looper.getMainLooper())

    private val _state = MutableLiveData(SearchScreenUi(loading = false))
    val state: LiveData<SearchScreenUi> = _state

    private var filter: String = ""

    private var job: Thread? = null

    fun updateSearchHistory(track: TrackUi) {
        tracksInteractor.updateSearchHistory(track)
    }

    fun clearSearchHistory() {
        tracksInteractor.clearSearchHistory()
        _state.value = _state.value?.copy(showHistory = false, data = emptyList())
    }

    fun searchInputChanged(text: String?, historyCanBeShown: Boolean?) {
        filter = text.orEmpty()
        handler.removeCallbacks(searchRunnable)
        if (text?.isEmpty() == true && historyCanBeShown == true) {
            val history = tracksInteractor.getSearchHistory().toList()
            _state.value = _state.value?.copy(
                showHistory = history.isNotEmpty(),
                data = history,
                loading = false
            )
        }
        if (text?.isNotEmpty() == true) {
            _state.value =
                _state.value?.copy(showHistory = false, data = emptyList(), loading = false)
            handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
        }
    }

    private fun search() {
        if (filter.isNotEmpty()) {
            _state.value = SearchScreenUi(loading = true)
            job?.interrupt()
            job = Thread {
                val tracks = tracksInteractor.searchTracks(filter)
                _state.postValue(
                    SearchScreenUi(
                        showError = tracks.error != null,
                        showEmptyState = tracks.content != null && tracks.content.isEmpty(),
                        data = tracks.content?.map { TrackUi.mapFrom(it) }.orEmpty()

                    ){
                        searchInputChanged(filter, null)
                    })

            }
            job?.start()
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

}