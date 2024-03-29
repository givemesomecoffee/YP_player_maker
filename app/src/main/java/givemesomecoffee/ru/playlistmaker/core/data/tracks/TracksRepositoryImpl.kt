package givemesomecoffee.ru.playlistmaker.core.data.tracks

import givemesomecoffee.ru.playlistmaker.core.data.local.db.FavouritesDao
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.TrackEntity
import givemesomecoffee.ru.playlistmaker.core.domain.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class TracksRepositoryImpl(
    private val api: TracksApi,
    private val favouritesDao: FavouritesDao
) : TracksRepository {

    override fun getTrack(id: String): TrackEntity {
        return api.getTrack(id).execute().body()?.results?.firstOrNull() ?: throw Exception("")
    }

    /**
     * костыль для кривой десериализации гсона и кривого апи айтюнса(отдает в поиске треки без медиа файлов)
     */
    override fun searchTrack(filter: String): Flow<Response<List<TrackEntity>>> {
        return flow {
            emit(
                Response(
                    error = null,
                    content = api.search(filter).results.filter { it.previewUrl != null } // removes corrupted entities
                )
            )
        }.catch {
            emit(Response(error = it, content = null))
        }
    }

    override suspend fun getFavouriteTracksId(): List<String> {
        return favouritesDao.getFavouriteTracksId()
    }
}