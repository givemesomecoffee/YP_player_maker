package givemesomecoffee.ru.playlistmaker.core.data.di

import android.content.Context
import givemesomecoffee.ru.playlistmaker.core.data.local.LocalStorageImpl
import givemesomecoffee.ru.playlistmaker.core.data.local.SearchHistoryStorage
import givemesomecoffee.ru.playlistmaker.core.data.local.SettingsStorage
import givemesomecoffee.ru.playlistmaker.core.data.tracks.TracksApi
import givemesomecoffee.ru.playlistmaker.core.data.tracks.TracksRepository
import givemesomecoffee.ru.playlistmaker.core.data.tracks.TracksRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<TracksApi> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TracksApi::class.java)
    }

    single {
        androidContext()
            .getSharedPreferences("practicum_example_preferences", Context.MODE_PRIVATE)
    }

    single<TracksRepository> {
        TracksRepositoryImpl(get())
    }

    single<SearchHistoryStorage> {
        LocalStorageImpl(get())
    }

    single<SettingsStorage> {
        LocalStorageImpl(get())
    }
}