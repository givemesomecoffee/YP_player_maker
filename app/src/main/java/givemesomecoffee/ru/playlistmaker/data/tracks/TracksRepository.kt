package givemesomecoffee.ru.playlistmaker.data.tracks

import givemesomecoffee.ru.playlistmaker.data.remote.Network
import givemesomecoffee.ru.playlistmaker.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.data.tracks.model.TracksResponse
import retrofit2.Response

interface TracksRepository {
    fun getTrack(id: String, onSuccess: (Track) -> Unit)
    fun searchTrack(
        filter: String,
        onSuccess: (Response<TracksResponse>) -> Unit,
        onError: (Throwable) -> Unit
    )

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