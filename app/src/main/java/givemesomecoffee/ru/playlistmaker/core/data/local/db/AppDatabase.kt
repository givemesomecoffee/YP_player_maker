package givemesomecoffee.ru.playlistmaker.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    version = 1,
    entities = [
        FavouriteTrack::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTracksDao(): FavouritesDao
}
