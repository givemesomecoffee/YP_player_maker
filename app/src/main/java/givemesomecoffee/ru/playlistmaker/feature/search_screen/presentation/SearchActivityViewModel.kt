package givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import givemesomecoffee.ru.playlistmaker.feature.search_screen.domain.TracksInteractor
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.SearchScreenUi
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TrackUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchActivityViewModel(private val tracksInteractor: TracksInteractor) :
    ViewModel() {

    private val _state = MutableLiveData(SearchScreenUi(loading = false))
    val state: LiveData<SearchScreenUi> = _state

    private var filter: String = ""

    private var searchJob: Job? = null

    fun updateSearchHistory(track: TrackUi) {
        tracksInteractor.updateSearchHistory(track)
    }

    fun clearSearchHistory() {
        tracksInteractor.clearSearchHistory()
        _state.value = _state.value?.copy(showHistory = false, data = emptyList())
    }

    fun searchInputChanged(text: String?, historyCanBeShown: Boolean?) {
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
            search(text)
        } else {
            searchJob?.cancel()
        }
    }

    private fun search(changedText: String) {
        if (filter == changedText) {
            return
        }

        filter = changedText

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            _state.value = SearchScreenUi(loading = true)
            withContext(Dispatchers.IO){
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

        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

}