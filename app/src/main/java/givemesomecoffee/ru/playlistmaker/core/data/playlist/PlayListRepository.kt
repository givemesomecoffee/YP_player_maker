package givemesomecoffee.ru.playlistmaker.core.data.playlist

import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist
import kotlinx.coroutines.flow.Flow

interface PlayListRepository {
    suspend fun createPlaylist(playlist: Playlist)

    fun trackPlaylists(): Flow<List<Playlist>>

    suspend fun addTrack(trackId: String, playlist: Playlist)

    suspend fun getPlaylist(id: String): Playlist
}