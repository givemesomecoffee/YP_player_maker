package givemesomecoffee.ru.playlistmaker.feature.search_screen.di

import givemesomecoffee.ru.playlistmaker.feature.search_screen.domain.TracksInteractor
import givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.SearchActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {

    single<TracksInteractor> {
        TracksInteractor(get(), get())
    }

    viewModel {
        SearchActivityViewModel(get())
    }

}