package givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation

import android.util.Log
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

    private var searchJob: Job? = null

    fun updateSearchHistory(track: TrackUi) {
        tracksInteractor.updateSearchHistory(track)
    }

    fun clearSearchHistory() {
        tracksInteractor.clearSearchHistory()
        _state.value = _state.value?.copy(showHistory = false, data = emptyList())
    }

    fun searchInputChanged(text: String?, historyCanBeShown: Boolean?) {
        viewModelScope.launch {
            if (text?.isEmpty() == true && historyCanBeShown == true) {
                val history = tracksInteractor.getSearchHistory().toList()
                val favourites = tracksInteractor.getFavouriteTracksIds()
                _state.value = _state.value?.copy(
                    showHistory = history.isNotEmpty(),
                    data = history.map { it.copy(isFavourite = it.trackId in favourites) },
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
    }

    private fun search(changedText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            _state.value = SearchScreenUi(loading = true)
            val favourites = tracksInteractor.getFavouriteTracksIds()
            withContext(Dispatchers.IO) {
                tracksInteractor.searchTracks(changedText).collect { result ->
                    Log.d("custom-test", result.toString())
                    _state.postValue(
                        SearchScreenUi(
                            showError = result.error != null,
                            showEmptyState = result.content != null && result.content.isEmpty(),
                            data = result.content?.map { TrackUi.mapFrom(it.copy(isFavourite = it.trackId in favourites)) }
                                .orEmpty()

                        ) {
                            searchInputChanged(changedText, null)
                        })
                }
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

}