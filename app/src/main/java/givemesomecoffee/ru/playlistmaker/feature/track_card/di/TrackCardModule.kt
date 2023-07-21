package givemesomecoffee.ru.playlistmaker.feature.track_card.di

import givemesomecoffee.ru.playlistmaker.feature.track_card.domain.TrackInteractor
import givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.TrackCardViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.add_to_playlist.AddToPlaylistViewModel

val trackCardModule = module {
    singleOf(::TrackInteractor)
    viewModelOf(::TrackCardViewModel)
    viewModelOf(::AddToPlaylistViewModel)
}