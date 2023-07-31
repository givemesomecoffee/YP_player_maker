package givemesomecoffee.ru.playlistmaker.feature.playlist_card.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import givemesomecoffee.ru.playlistmaker.feature.playlist_card.ui.PlaylistCardViewModel
import org.koin.core.module.dsl.singleOf
import givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain.GetPlaylistUseCase
import givemesomecoffee.ru.playlistmaker.feature.playlist_card.domain.GetTracksUseCase

val playlistCardModule = module {
    singleOf(::GetPlaylistUseCase)
    singleOf(::GetTracksUseCase)
    viewModelOf(::PlaylistCardViewModel)
}