package givemesomecoffee.ru.playlistmaker.core.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlists")
    fun subscribePlaylists(): Flow<List<PlaylistEntity>>

    @Query("SELECT * FROM playlists")
    fun getPlaylists(): List<PlaylistEntity>

    @Query("DELETE FROM playlists WHERE id = :id")
    suspend fun deletePlaylist(id: String)

    @Query("SELECT * FROM playlists  WHERE id = :id")
    suspend fun getPlaylist(id: String): PlaylistEntity

    @Query("SELECT * FROM playlists  WHERE id = :id")
    fun trackPlaylist(id: String): Flow<PlaylistEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistTrack(track: PlaylistTrackEntity)

    @Query("SELECT * FROM playlist_tracks WHERE trackId IN (:ids)")
    fun getPlaylistTracks(ids: List<String>): List<PlaylistTrackEntity>

    @Query("DELETE FROM playlist_tracks WHERE trackId = :id")
    suspend fun deleteTrack(id: String)

}