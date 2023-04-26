package givemesomecoffee.ru.playlistmaker.core.data.tracks.model

data class TracksResponse(
    val resultCount: Int,
    val results: List<Track>
)