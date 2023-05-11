package givemesomecoffee.ru.playlistmaker.core.di

import givemesomecoffee.ru.playlistmaker.core.data.di.dataModule
import givemesomecoffee.ru.playlistmaker.core.presentation.player.playerProvider

object CoreComponent {
    val modules = listOf(dataModule, playerProvider)
}