package givemesomecoffee.ru.playlistmaker.core.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track

@Entity(tableName = "playlist_tracks")
data class PlaylistTrackEntity (
    override val trackName: String,
    override val artistName: String,
    override val trackTimeMillis: Long,
    override val artworkUrl100: String,
    @PrimaryKey
    override val trackId: String,
    override val collectionName: String?,
    override val releaseDate: String?,
    override val primaryGenreName: String,
    override val country: String,
    override val previewUrl: String,
    override val isFavourite: Boolean
): Track {
    companion object{
        fun mapFrom(track: Track): PlaylistTrackEntity {
            return PlaylistTrackEntity(
                track.trackName,
                track.artistName,
                track.trackTimeMillis,
                track.artworkUrl100,
                track.trackId,
                track.collectionName,
                track.releaseDate.orEmpty(),
                track.primaryGenreName,
                track.country,
                track.previewUrl,
                false
            )
        }
    }
}