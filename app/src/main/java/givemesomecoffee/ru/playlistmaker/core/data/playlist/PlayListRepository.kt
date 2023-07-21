package givemesomecoffee.ru.playlistmaker.core.data.playlist

import kotlinx.coroutines.flow.Flow

interface PlayListRepository {
    suspend fun createPlaylist(playlist: Playlist)

    fun trackPlaylists(): Flow<List<Playlist>>

    suspend fun addTrack(trackId: String, playlist: Playlist)
}