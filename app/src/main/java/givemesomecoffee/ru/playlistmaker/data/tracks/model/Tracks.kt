package givemesomecoffee.ru.playlistmaker.data.tracks.model

data class TracksResponse(
    val resultCount: Int,
    val results: List<Track>
)