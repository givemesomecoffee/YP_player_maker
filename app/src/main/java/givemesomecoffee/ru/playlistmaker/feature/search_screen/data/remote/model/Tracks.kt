package givemesomecoffee.ru.playlistmaker.feature.search_screen.model

import givemesomecoffee.ru.playlistmaker.feature.search_screen.data.remote.model.Track

data class TracksResponse(
    val resultCount: Int,
    val results: List<Track>
)