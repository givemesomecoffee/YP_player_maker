package givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain

import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepository
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPlaylistUseCase(
    private val playListRepository: PlayListRepository
) {

    suspend fun invoke(id: String): Playlist {
        return withContext(Dispatchers.IO){
          playListRepository.getPlaylist(id)
        }
    }
}