package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.add_to_playlist

sealed class AddToPlayListEvents {
    class AlreadyAddedToPlaylist(val name: String) : AddToPlayListEvents()
    class AddedToPlayList(val name: String) : AddToPlayListEvents()
}