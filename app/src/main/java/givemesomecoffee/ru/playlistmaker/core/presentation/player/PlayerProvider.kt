package givemesomecoffee.ru.playlistmaker.core.presentation.player

import org.koin.dsl.module

val playerProvider = module{
    factory<PlayerHolder> {
        Player()
    }
}