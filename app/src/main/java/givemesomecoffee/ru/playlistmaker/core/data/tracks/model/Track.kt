package givemesomecoffee.ru.playlistmaker.core.data.tracks.model

import givemesomecoffee.ru.playlistmaker.core.domain.track.TrackModel

interface Track {
    val trackName: String
    val artistName: String
    val trackTimeMillis: Long
    val artworkUrl100: String
    val trackId: String
    val collectionName: String?
    val releaseDate: String?
    val primaryGenreName: String
    val country: String
    val previewUrl: String
    val isFavourite: Boolean

    fun toDomain(): TrackModel {
        return TrackModel(
            trackName,
            artistName,
            trackTimeMillis,
            artworkUrl100,
            trackId,
            collectionName,
            releaseDate,
            primaryGenreName,
            country,
            previewUrl
        )
    }
}