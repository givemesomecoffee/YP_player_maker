package givemesomecoffee.ru.playlistmaker.data

import givemesomecoffee.ru.playlistmaker.feature.search_screen.data.local.LocalTracksStorage
import givemesomecoffee.ru.playlistmaker.feature.search_screen.data.remote.TracksApi
import givemesomecoffee.ru.playlistmaker.feature.search_screen.data.remote.model.Track
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TracksResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TracksRepository(
    private val api: TracksApi
) {

    fun getTrack(id: String, onSuccess: (Track) -> Unit) {
        LocalTracksStorage.tracks?.firstOrNull { it.trackId == id }.let {
            if (it != null) {
                onSuccess(it)
            } else {
                api.getTrack(id).enqueue(object : Callback<TracksResponse> {
                    override fun onResponse(
                        call: Call<TracksResponse>,
                        response: Response<TracksResponse>
                    ) {
                        response.body()?.results?.firstOrNull().let {
                            if (it == null) {
                                throw Exception("Internal")
                            } else {
                                onSuccess(it)
                            }
                        }
                    }

                    override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                        throw t
                    }

                })
            }
        }
    }

    fun searchTrack(
        filter: String,
        onSuccess: (Response<TracksResponse>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        api.search(filter).enqueue(object : Callback<TracksResponse> {
            override fun onResponse(
                call: Call<TracksResponse>,
                response: Response<TracksResponse>
            ) {
                onSuccess(response)
            }

            override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                onError(t)
            }
        })
    }

    companion object {
        private var INSTANCE: TracksRepository? = null

        fun getInstance(): TracksRepository {
            return INSTANCE ?: synchronized(this) {
                TracksRepository(Network.retrofit.create(TracksApi::class.java)).also {
                    INSTANCE = it
                }
            }
        }
    }
}