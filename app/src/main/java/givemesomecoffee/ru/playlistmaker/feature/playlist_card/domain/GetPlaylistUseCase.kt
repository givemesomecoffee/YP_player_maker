package givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain

import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepository
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull

class GetPlaylistUseCase(
    private val playListRepository: PlayListRepository
) {

    fun invoke(id: Long): Flow<Playlist> {
        return playListRepository.trackPlaylist(id).filterNotNull()
    }
}