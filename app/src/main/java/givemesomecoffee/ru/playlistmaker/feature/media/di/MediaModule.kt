package givemesomecoffee.ru.playlistmaker.feature.media.di

import givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.FavouriteTracksInteractor
import givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.create_playlist.CreatePlayListUseCase
import givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.FavouritesViewModel
import givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.PlaylistsViewModel
import givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.create_playlist.PlayListCreateViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val mediaModule = module {
    singleOf(::FavouriteTracksInteractor)
    singleOf(::CreatePlayListUseCase)
    viewModelOf(::PlaylistsViewModel)
    viewModelOf(::FavouritesViewModel)
    viewModelOf(::PlayListCreateViewModel)
}