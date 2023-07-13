package givemesomecoffee.ru.playlistmaker.core.data.favourites

import givemesomecoffee.ru.playlistmaker.core.data.local.db.FavouriteTrack
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import kotlinx.coroutines.flow.Flow

interface FavouriteTracksRepository {

    suspend fun removeFromFavourites(trackId: String)

    suspend fun addToFavourites(track: Track)

    fun getFavouriteTracks(): Flow<List<FavouriteTrack>>
}