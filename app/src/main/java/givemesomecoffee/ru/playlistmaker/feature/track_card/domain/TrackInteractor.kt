package givemesomecoffee.ru.playlistmaker.feature.track_card.domain

import givemesomecoffee.ru.playlistmaker.core.data.local.LocalTracksStorage
import givemesomecoffee.ru.playlistmaker.core.data.tracks.TracksRepository
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.core.domain.Response

class TrackInteractor(private val tracksApi: TracksRepository) {

    fun getTrack(id: String): Response<Track> {
        LocalTracksStorage.tracks?.firstOrNull { it.trackId == id }
            ?.let { return Response(content = it, error = null) }

        val result = tracksApi.getTrack(id)

        return Response(
            null, result
        )
    }
}
