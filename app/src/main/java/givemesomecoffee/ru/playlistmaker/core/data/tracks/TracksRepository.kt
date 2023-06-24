package givemesomecoffee.ru.playlistmaker.core.data.tracks

import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.core.domain.Response
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    fun getTrack(id: String): Track
    fun searchTrack(filter: String): Flow<Response<List<Track>>>
}