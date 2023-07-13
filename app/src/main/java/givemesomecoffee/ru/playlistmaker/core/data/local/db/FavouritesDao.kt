package givemesomecoffee.ru.playlistmaker.core.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteTrack(track: FavouriteTrack)

    @Query("SELECT * FROM favourite_tracks")
    fun subscribeFavouriteTracks(): Flow<List<FavouriteTrack>>

    @Query("SELECT trackId FROM favourite_tracks")
    suspend fun getFavouriteTracksId(): List<String>

    @Query("DELETE FROM favourite_tracks WHERE trackId = :trackId")
    suspend fun deleteTrack(trackId: String)

}