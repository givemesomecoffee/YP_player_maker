package givemesomecoffee.ru.playlistmaker.core.data.di

import android.content.Context
import androidx.room.Room
import givemesomecoffee.ru.playlistmaker.core.data.favourites.FavouriteTracksRepository
import givemesomecoffee.ru.playlistmaker.core.data.favourites.FavouriteTacksRepositoryImpl
import givemesomecoffee.ru.playlistmaker.core.data.local.db.AppDatabase
import givemesomecoffee.ru.playlistmaker.core.data.local.LocalStorageImpl
import givemesomecoffee.ru.playlistmaker.core.data.local.SearchHistoryStorage
import givemesomecoffee.ru.playlistmaker.core.data.local.SettingsStorage
import givemesomecoffee.ru.playlistmaker.core.data.local.db.FavouritesDao
import givemesomecoffee.ru.playlistmaker.core.data.local.db.PlaylistsDao
import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepository
import givemesomecoffee.ru.playlistmaker.core.data.tracks.TracksApi
import givemesomecoffee.ru.playlistmaker.core.data.tracks.TracksRepository
import givemesomecoffee.ru.playlistmaker.core.data.tracks.TracksRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import givemesomecoffee.ru.playlistmaker.core.data.playlist.PlayListRepositoryImpl

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

    singleOf(::TracksRepositoryImpl).bind<TracksRepository>()
    singleOf(::FavouriteTacksRepositoryImpl).bind<FavouriteTracksRepository>()
    singleOf(::LocalStorageImpl).apply {
        bind<SearchHistoryStorage>()
        bind<SettingsStorage>()
    }

    singleOf(::PlayListRepositoryImpl).bind<PlayListRepository>()
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "database.db")
            .build()
    }

    single<FavouritesDao> {
        val database = get<AppDatabase>()
        database.getTracksDao()
    }

    single<PlaylistsDao> {
        val database = get<AppDatabase>()
        database.getPlayListDao()
    }

}