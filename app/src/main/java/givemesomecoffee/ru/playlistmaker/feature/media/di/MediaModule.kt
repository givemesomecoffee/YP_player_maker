package givemesomecoffee.ru.playlistmaker.feature.media.di

import givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.FavouritesViewModel
import givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.PlaylistsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mediaModule = module {

    viewModel {
        FavouritesViewModel()
    }

    viewModel {
        PlaylistsViewModel()
    }

}