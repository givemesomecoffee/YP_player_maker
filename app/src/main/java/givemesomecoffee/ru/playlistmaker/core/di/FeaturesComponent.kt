package givemesomecoffee.ru.playlistmaker.core.di

import givemesomecoffee.ru.playlistmaker.feature.search_screen.di.searchModule
import givemesomecoffee.ru.playlistmaker.feature.settings.di.settingsModule
import givemesomecoffee.ru.playlistmaker.feature.track_card.di.trackCardModule

object FeaturesComponent {
    val modules = listOf(searchModule, trackCardModule, settingsModule)
}