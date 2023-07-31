package givemesomecoffee.ru.playlistmaker.core.data.tracks

import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.TracksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TracksApi {
    @GET("search?entity=song")
    suspend fun search(@Query("term") text: String): TracksResponse

    @GET("lookup")
    fun getTrack(@Query("id") id: String): Call<TracksResponse>

    @GET("lookup")
    suspend fun getTracks(@Query("id") ids: String): TracksResponse
}