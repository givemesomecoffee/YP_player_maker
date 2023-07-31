package givemesomecoffee.ru.playlistmaker.feature.media.domain

import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepository
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist

class GetPlaylistUseCase(
    private val playListRepository: PlayListRepository
) {
    suspend fun invoke(id: Long): Playlist {
        return playListRepository.getPlaylist(id)
    }
}