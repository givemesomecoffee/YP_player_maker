package givemesomecoffee.ru.playlistmaker.data.tracks

import givemesomecoffee.ru.playlistmaker.data.local.LocalTracksStorage
import givemesomecoffee.ru.playlistmaker.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.data.tracks.model.TracksResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TracksRepositoryImpl(
    private val api: TracksApi
): TracksRepository {

    override fun getTrack(id: String, onSuccess: (Track) -> Unit) {
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

    override fun searchTrack(
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
}