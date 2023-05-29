package givemesomecoffee.ru.playlistmaker.core.data.tracks

import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.core.domain.Response

class TracksRepositoryImpl(
    private val api: TracksApi
) : TracksRepository {

    override fun getTrack(id: String): Track {
        return api.getTrack(id).execute().body()?.results?.firstOrNull() ?: throw Exception("")
    }

    override fun searchTrack(filter: String): Response<List<Track>> {
        return try {
            Response(
                error = null,
                content = api.search(filter).execute().body()!!.results
            )
        } catch (t: Throwable) {
            Response(error = t, content = null)
        }
    }
}