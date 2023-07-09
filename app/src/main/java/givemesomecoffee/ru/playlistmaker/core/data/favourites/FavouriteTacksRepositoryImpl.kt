package givemesomecoffee.ru.playlistmaker.core.data.favourites

import givemesomecoffee.ru.playlistmaker.core.data.local.db.FavouriteTrack
import givemesomecoffee.ru.playlistmaker.core.data.local.db.FavouritesDao
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import kotlinx.coroutines.flow.Flow

class FavouriteTacksRepositoryImpl(
    private val favouritesDao: FavouritesDao
) : FavouriteTracksRepository {
    override suspend fun removeFromFavourites(trackId: String) {
        favouritesDao.deleteTrack(trackId)
    }

    override suspend fun addToFavourites(track: Track) {
        favouritesDao.insertFavouriteTrack(FavouriteTrack.mapFrom(track))
    }

    override fun getFavouriteTracks(): Flow<List<FavouriteTrack>> {
        return favouritesDao.subscribeFavouriteTracks()
    }

}