package givemesomecoffee.ru.playlistmaker.core.data.tracks.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackEntity(
    override val trackName: String,
    override val artistName: String,
    override val trackTimeMillis: Long,
    override val artworkUrl100: String,
    override val artworkUrl160: String?,
    override val trackId: String,
    override val collectionName: String?,
    override val releaseDate: String?,
    override val primaryGenreName: String,
    override val country: String,
    override val previewUrl: String,
    override val isFavourite: Boolean = false

) : Track, Parcelable
