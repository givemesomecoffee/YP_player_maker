package givemesomecoffee.ru.playlistmaker.core.data.tracks

import givemesomecoffee.ru.playlistmaker.core.data.remote.Network
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.core.domain.Response

interface TracksRepository {
    fun getTrack(id: String): Track
    fun searchTrack(filter: String): Response<List<Track>>

    companion object {
        private var INSTANCE: TracksRepository? = null

        fun getInstance(): TracksRepository {
            return INSTANCE ?: synchronized(this) {
                TracksRepositoryImpl(Network.retrofit.create(TracksApi::class.java)).also {
                    INSTANCE = it
                }
            }
        }
    }
}