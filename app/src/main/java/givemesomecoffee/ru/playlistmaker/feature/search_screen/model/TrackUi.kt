package givemesomecoffee.ru.playlistmaker.feature.search_screen.model

import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import java.text.SimpleDateFormat
import java.util.*

data class TrackUi(
    val trackName: String,
    val trackId: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl: String,
    val isFavourite: Boolean,
    val trackSource: String
) {
    companion object {
        fun mapFrom(entity: Track): TrackUi {
            return TrackUi(
                trackId = entity.trackId,
                trackName = entity.trackName,
                artistName = entity.artistName,
                trackTime = SimpleDateFormat("mm:ss", Locale.getDefault()).format(entity.trackTimeMillis),
                artworkUrl = entity.artworkUrl160 ?: entity.artworkUrl100,
                trackSource = entity.previewUrl,
                isFavourite = entity.isFavourite
            )
        }
    }
}