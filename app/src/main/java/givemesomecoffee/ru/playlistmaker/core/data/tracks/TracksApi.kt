package givemesomecoffee.ru.playlistmaker.core.data.tracks

import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.TracksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TracksApi {
    @GET("search?entity=song")
    fun search(@Query("term") text: String) : Call<TracksResponse>

    @GET("lookup")
    fun getTrack(@Query("id") id: String): Call<TracksResponse>
}