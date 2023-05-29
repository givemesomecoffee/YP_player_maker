package givemesomecoffee.ru.playlistmaker.feature.settings.di

import givemesomecoffee.ru.playlistmaker.feature.settings.SettingsViewModel
import givemesomecoffee.ru.playlistmaker.feature.settings.domain.SettingsInteractor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module{

    single<SettingsInteractor> {
        SettingsInteractor(get())
    }

    viewModel {
        SettingsViewModel(get())
    }
}