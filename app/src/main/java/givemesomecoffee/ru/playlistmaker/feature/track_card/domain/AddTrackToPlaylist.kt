package givemesomecoffee.ru.playlistmaker.feature.track_card.domain

import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepository
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist

class AddTrackToPlaylist(
    private val playListRepository: PlayListRepository
) {

    suspend fun invoke(trackId: String, playlist: Playlist) {
        playListRepository.addTrack(trackId, playlist)
    }
}