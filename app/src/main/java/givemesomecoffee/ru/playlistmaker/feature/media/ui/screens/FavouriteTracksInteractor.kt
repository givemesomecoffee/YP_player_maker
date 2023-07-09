package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens

import givemesomecoffee.ru.playlistmaker.core.data.favourites.FavouriteTracksRepository
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class FavouriteTracksInteractor(
    private val repository: FavouriteTracksRepository
) {

    fun getFavouriteTracks(): Flow<List<Track>> {
        return repository.getFavouriteTracks()
            .transform { emit(it.sortedByDescending { it.timestamp }) }
    }
}