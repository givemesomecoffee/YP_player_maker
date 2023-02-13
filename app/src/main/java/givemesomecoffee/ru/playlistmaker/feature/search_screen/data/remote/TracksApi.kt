package givemesomecoffee.ru.playlistmaker.feature.search_screen.data.remote

import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TracksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TracksApi {
    @GET("search?entity=song")
    fun search(@Query("term") text: String) : Call<TracksResponse>
}