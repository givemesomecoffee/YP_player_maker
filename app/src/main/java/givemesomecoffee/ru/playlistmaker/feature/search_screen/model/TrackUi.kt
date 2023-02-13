package givemesomecoffee.ru.playlistmaker.feature.search_screen.model

import givemesomecoffee.ru.playlistmaker.feature.search_screen.data.remote.model.Track
import java.text.SimpleDateFormat
import java.util.*

data class TrackUi(
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String
) {
    companion object {
        fun mapFrom(entity: Track): TrackUi {
            return TrackUi(
                trackName = entity.trackName,
                artistName = entity.artistName,
                trackTime = SimpleDateFormat("mm:ss", Locale.getDefault()).format(entity.trackTime),
                artworkUrl100 = entity.artworkUrl100
            )
        }
    }
}