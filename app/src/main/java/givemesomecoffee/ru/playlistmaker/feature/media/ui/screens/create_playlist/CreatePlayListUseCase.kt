package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.create_playlist

import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepository
import givemesomecoffee.ru.playlistmaker.core.data.playlist.Playlist

class CreatePlayListUseCase(
    private val playlistsRepositoryImpl: PlayListRepository
) {

    suspend fun invoke(playlist: Playlist): Boolean{
        playlistsRepositoryImpl.createPlaylist(playlist)
        return true
    }
}