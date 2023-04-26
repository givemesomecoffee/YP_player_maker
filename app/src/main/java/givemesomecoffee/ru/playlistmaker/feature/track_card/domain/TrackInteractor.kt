package givemesomecoffee.ru.playlistmaker.feature.track_card.domain

import givemesomecoffee.ru.playlistmaker.core.data.local.LocalTracksStorage
import givemesomecoffee.ru.playlistmaker.core.data.tracks.TracksRepository
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.core.domain.Response

class TrackInteractor {

    private val tracksApi = TracksRepository.getInstance()

    fun getTrack(id: String): Response<Track> {
        LocalTracksStorage.tracks?.firstOrNull { it.trackId == id }
            ?.let { return Response(content = it, error = null) }

        val result = tracksApi.searchTrack(id)
        return Response(
            result.error, result.content?.firstOrNull()
        ).takeIf { result.content?.firstOrNull() != null } ?: Response(
            result.error ?: Exception(""), null
        )
    }
}
