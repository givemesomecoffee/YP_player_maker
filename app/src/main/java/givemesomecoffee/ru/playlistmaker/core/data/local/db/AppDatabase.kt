package givemesomecoffee.ru.playlistmaker.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    version = 1,
    entities = [
        FavouriteTrack::class,
        PlaylistEntity::class
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTracksDao(): FavouritesDao

    abstract fun getPlayListDao(): PlaylistsDao
}
