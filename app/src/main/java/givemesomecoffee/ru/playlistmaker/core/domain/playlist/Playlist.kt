package givemesomecoffee.ru.playlistmaker.core.domain.playlist

import givemesomecoffee.ru.playlistmaker.core.data.local.db.PlaylistEntity

interface Playlist {
    val id: Long?
    val name: String
    val description: String
    val path: String?
    val tracks: List<String>
    val size: Int

    fun toLocal(): PlaylistEntity {
        return PlaylistEntity(
            id = id,
            name = name,
            description, path, tracks, size
        )
    }
}