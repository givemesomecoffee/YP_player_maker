package givemesomecoffee.ru.playlistmaker.core.domain.track

import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track

data class TrackModel(
    override val trackName: String,
    override val artistName: String,
    override val trackTimeMillis: Long,
    override val artworkUrl100: String,
    override val trackId: String,
    override val collectionName: String?,
    override val releaseDate: String?,
    override val primaryGenreName: String,
    override val country: String,
    override val previewUrl: String,
    override val isFavourite: Boolean = false
) : Track

