package givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain

import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepository
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track

class GetTracksUseCase(
    private val playListRepository: PlayListRepository
) {
    suspend fun invoke(ids: List<String>): List<Track>{
        return playListRepository.getPlaylistTracks(ids)
    }
}