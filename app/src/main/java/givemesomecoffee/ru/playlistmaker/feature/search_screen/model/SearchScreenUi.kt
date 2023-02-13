package givemesomecoffee.ru.playlistmaker.feature.search_screen.model

import retrofit2.Response

class SearchScreenUi(
    val showError: Boolean = false,
    val showEmptyState: Boolean = false,
    val data: List<TrackUi> = emptyList(),
    val loading: Boolean = false
) {
    companion object {
        fun mapFrom(data: Response<TracksResponse>): SearchScreenUi {
            if (data.code() != 200) return SearchScreenUi(showError = true)
            return SearchScreenUi(
                data = data.body()?.results?.map { TrackUi.mapFrom(it) } ?: emptyList(),
                showEmptyState = (data.body()?.resultCount ?: 0) == 0
            )
        }

        fun mapFrom(t: Throwable): SearchScreenUi {
            return SearchScreenUi(showError = true)
        }
    }
}