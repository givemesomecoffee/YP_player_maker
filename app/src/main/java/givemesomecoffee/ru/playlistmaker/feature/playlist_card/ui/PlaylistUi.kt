package givemesomecoffee.ru.playlistmaker.feature.playlist_card.ui

import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist

data class PlaylistUi(
    override val id: Long?,
    override val name: String,
    override val description: String,
    override val path: String?,
    override val tracks: List<String>,
    override val size: Int,
    val duration: Long,
    val tracksList: List<Track>
) : Playlist {
    companion object{
        fun mapFrom(playlist: Playlist, tracks: List<Track>): PlaylistUi {
            return PlaylistUi(
                playlist.id,
                playlist.name,
                playlist.description,
                playlist.path,
                playlist.tracks,
                playlist.size,
                tracks.sumOf { it.trackTimeMillis },
                tracks
            )
        }
    }
}