package givemesomecoffee.ru.playlistmaker.core.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long? = null,
    override val name: String,
    override val description: String = "",
    override val path: String? = null,
    override val tracks: List<String> = emptyList(),
    override val size: Int = 0
): Playlist