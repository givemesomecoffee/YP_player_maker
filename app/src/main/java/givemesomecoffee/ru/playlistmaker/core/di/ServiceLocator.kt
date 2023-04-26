package givemesomecoffee.ru.playlistmaker.core.di

import givemesomecoffee.ru.playlistmaker.core.presentation.player.Player
import givemesomecoffee.ru.playlistmaker.core.presentation.player.PlayerHolder

object ServiceLocator {
    fun resolvePlayerHolder(): PlayerHolder = Player()
}