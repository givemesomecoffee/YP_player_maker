package givemesomecoffee.ru.playlistmaker.feature.track_card.model

import android.content.Context
import givemesomecoffee.ru.playlistmaker.feature.search_screen.data.remote.model.Track
import java.text.SimpleDateFormat
import java.util.*

data class TrackUi(
    val trackName: String,
    val trackId: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String,
    val info: List<InfoOption>,
    val trackUrl: String
) {
    companion object {
        fun mapFrom(entity: Track, context: Context): TrackUi {
            return TrackUi(
                trackId = entity.trackId,
                trackName = entity.trackName,
                artistName = entity.artistName,
                trackTime = SimpleDateFormat("mm:ss", Locale.getDefault()).format(entity.trackTimeMillis),
                artworkUrl100 = entity.artworkUrl100,
                info = InfoOption.mapToList(entity, context),
                trackUrl = entity.previewUrl
            )
        }
    }
}