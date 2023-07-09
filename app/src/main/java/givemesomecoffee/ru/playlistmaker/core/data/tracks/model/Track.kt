package givemesomecoffee.ru.playlistmaker.core.data.tracks.model

interface Track {
    val trackName: String
    val artistName: String
    val trackTimeMillis: Long
    val artworkUrl100: String
    val trackId: String
    val collectionName: String?
    val releaseDate: String
    val primaryGenreName: String
    val country: String
    val previewUrl: String
    val isFavourite: Boolean
}