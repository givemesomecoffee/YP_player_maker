package givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain

import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepository

class DeleteTrackUseCase(
    private val playListRepository: PlayListRepository
) {

    suspend fun invoke(trackId: String, playlistId: Long){
        playListRepository.deleteTrack(trackId, playlistId)
    }
}