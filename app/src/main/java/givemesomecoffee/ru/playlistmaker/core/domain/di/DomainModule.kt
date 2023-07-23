package givemesomecoffee.ru.playlistmaker.core.domain.di

import givemesomecoffee.ru.playlistmaker.core.domain.playlist.TrackPlayLists
import org.koin.dsl.module

val domainModule = module {
    single<TrackPlayLists> {
        TrackPlayLists(get())
    }
}