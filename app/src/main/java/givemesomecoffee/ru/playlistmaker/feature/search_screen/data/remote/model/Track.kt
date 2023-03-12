package givemesomecoffee.ru.playlistmaker.feature.search_screen.data.remote.model

data class Track(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String,
    val trackId: String,
    val collectionName: String?,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String

)
