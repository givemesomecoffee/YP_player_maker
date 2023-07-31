package givemesomecoffee.ru.playlistmaker.core.data.playlist

import givemesomecoffee.ru.playlistmaker.core.data.local.db.PlaylistTrackEntity
import givemesomecoffee.ru.playlistmaker.core.data.local.db.PlaylistsDao
import givemesomecoffee.ru.playlistmaker.core.data.tracks.TracksApi
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PlayListRepositoryImpl(
    private val playlistsDao: PlaylistsDao,
    private val tracksApi: TracksApi
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
        playlistsDao.insertPlaylistTrack(PlaylistTrackEntity.mapFrom(tracksApi.getTracks(trackId).results.first()))
    }

    override fun trackPlaylist(playlistId: String) = playlistsDao.trackPlaylist(playlistId)
    override suspend fun getPlaylistTracks(ids: List<String>): List<Track> {
        return withContext(Dispatchers.IO){
            playlistsDao.getPlaylistTracks(ids)
        }
    }

    override suspend fun deleteTrack(trackId: String, playlistId: String) {
        withContext(Dispatchers.IO) {
            val prev = playlistsDao.getPlaylist(playlistId)
            val tracks = prev.tracks.toMutableList()
            tracks.remove(trackId)
            playlistsDao.insertPlaylist(prev.copy(tracks = tracks, size = tracks.size))
            playlistsDao.getPlaylists().mapNotNull { it.tracks.firstOrNull { it == trackId } }
                .takeIf {
                    it.isNotEmpty()
                }?.let { playlistsDao.deleteTrack(trackId) }
        }
    }

}