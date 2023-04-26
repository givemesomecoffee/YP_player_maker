package givemesomecoffee.ru.playlistmaker.feature.search_screen.model

data class SearchScreenUi(
    val showError: Boolean = false,
    val showEmptyState: Boolean = false,
    val data: List<TrackUi> = emptyList(),
    val showHistory: Boolean = false,
    val loading: Boolean = false,
    val errorCallback: (() -> Unit)? = null,
)