package givemesomecoffee.ru.playlistmaker.feature.media.domain

import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepository
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist

class CreatePlayListUseCase(
    private val playlistsRepositoryImpl: PlayListRepository
) {

    suspend fun invoke(playlist: Playlist): Boolean{
        playlistsRepositoryImpl.createPlaylist(playlist)
        return true
    }
}