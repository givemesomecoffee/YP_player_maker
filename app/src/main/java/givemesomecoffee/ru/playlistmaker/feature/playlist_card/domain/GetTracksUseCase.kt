package givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain

import givemesomecoffee.ru.playlistmaker.core.data.tracks.TracksRepository
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track

class GetTracksUseCase(
    private val tracksRepository: TracksRepository
) {
    suspend fun invoke(ids: List<String>): List<Track>{
        return tracksRepository.getTracks(ids).results
    }
}