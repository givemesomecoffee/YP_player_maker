package givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain

import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepository
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist

class DeletePlaylistUseCase(
    private val playListRepository: PlayListRepository
) {
    suspend fun invoke(playlist: Playlist){
        playListRepository.deletePlaylist(playlist)
    }
}