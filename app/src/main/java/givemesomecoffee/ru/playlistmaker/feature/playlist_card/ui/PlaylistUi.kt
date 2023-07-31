package givemesomecoffee.ru.playlistmaker.feature.playlist_card.ui

import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.resolveTracksCounter
import java.text.SimpleDateFormat
import java.util.Locale

data class PlaylistUi(
    override val id: Long?,
    override val name: String,
    override val description: String,
    override val path: String?,
    override val tracks: List<String>,
    override val size: Int,
    val duration: Long,
    val tracksList: List<Track>,
    val shareText: String
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
                tracks,
                resolveShareText(playlist.name, playlist.description, tracks)
            )
        }

        private fun resolveShareText(
            name: String,
            description: String,
            tracks: List<Track>
        ): String {
            val nextLine = "\n"
            val space = " "
            return buildString {
                append(name)
                append(nextLine)
                append(description)
                append(nextLine)
                append(tracks.size.resolveTracksCounter())
                append(nextLine)
                tracks.forEachIndexed { index, track ->
                    append(index + 1)
                    append(space)
                    append(track.artistName)
                    append(" - ")
                    append(track.trackName)
                    append("(${
                        SimpleDateFormat(
                        "mm:ss",
                        Locale.getDefault()
                    ).format(track.trackTimeMillis)
                    })")
                    append(nextLine)
                }
            }
        }
    }
}