package givemesomecoffee.ru.playlistmaker.core.data.tracks

import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.TrackEntity
import givemesomecoffee.ru.playlistmaker.core.domain.Response
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    fun getTrack(id: String): TrackEntity
    fun searchTrack(filter: String): Flow<Response<List<TrackEntity>>>

    suspend fun getFavouriteTracksId(): List<String>
}