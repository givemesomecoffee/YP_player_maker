package givemesomecoffee.ru.playlistmaker.feature.track_card.di

import givemesomecoffee.ru.playlistmaker.feature.track_card.domain.TrackInteractor
import givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.TrackCardViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val trackCardModule = module {

    single<TrackInteractor> {
        TrackInteractor(get())
    }
    viewModel {
        TrackCardViewModel(androidApplication(), get(), get())
    }
}