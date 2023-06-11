package givemesomecoffee.ru.playlistmaker.core.data.tracks

import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.core.domain.Response

interface TracksRepository {
    fun getTrack(id: String): Track
    fun searchTrack(filter: String): Response<List<Track>>
}