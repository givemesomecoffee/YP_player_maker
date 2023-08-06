package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.create_playlist

sealed class CreatePlayListEvents {

    class Created(val name: String): CreatePlayListEvents()

}