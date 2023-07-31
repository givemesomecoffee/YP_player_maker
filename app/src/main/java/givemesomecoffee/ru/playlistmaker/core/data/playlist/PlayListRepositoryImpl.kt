package givemesomecoffee.ru.playlistmaker.core.data.playlist

import givemesomecoffee.ru.playlistmaker.core.data.local.db.PlaylistsDao
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist
import kotlinx.coroutines.flow.Flow

class PlayListRepositoryImpl(
    private val playlistsDao: PlaylistsDao
): PlayListRepository {

    override suspend fun createPlaylist(playlist: Playlist){
        playlistsDao.insertPlaylist(playlist.toLocal())
    }

    override fun trackPlaylists(): Flow<List<Playlist>> {
        return playlistsDao.subscribePlaylists()
    }

    override suspend fun addTrack(trackId: String, playlist: Playlist) {
        val tracks = buildList {
            addAll(playlist.tracks)
            add(trackId)
        }
        playlistsDao.insertPlaylist(playlist.toLocal().copy(tracks = tracks, size = tracks.size))
    }

    override suspend fun getPlaylist(id: String): Playlist = playlistsDao.getPlaylist(id)
}