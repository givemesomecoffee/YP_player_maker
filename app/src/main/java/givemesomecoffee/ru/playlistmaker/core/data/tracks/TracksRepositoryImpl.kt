package givemesomecoffee.ru.playlistmaker.core.data.tracks

import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.core.domain.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TracksRepositoryImpl(
    private val api: TracksApi
) : TracksRepository {

    override fun getTrack(id: String): Track {
        return api.getTrack(id).execute().body()?.results?.firstOrNull() ?: throw Exception("")
    }

    override fun searchTrack(filter: String): Flow<Response<List<Track>>> {
        return flow {
            try {
                emit(Response(
                    error = null,
                    content = api.search(filter).results
                ))
            } catch (t: Throwable) {
               emit( Response(error = t, content = null))
            }
        }
    }
}