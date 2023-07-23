package givemesomecoffee.ru.playlistmaker.core.domain.playlist

import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepository
import kotlinx.coroutines.flow.Flow

class TrackPlayLists(
    private val playlistsRepository: PlayListRepository
) {
    fun invoke(): Flow<List<Playlist>> {
        return playlistsRepository.trackPlaylists()
    }
}