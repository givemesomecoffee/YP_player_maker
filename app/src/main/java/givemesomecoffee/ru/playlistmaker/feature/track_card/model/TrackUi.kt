package givemesomecoffee.ru.playlistmaker.feature.track_card.model

import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.FavouriteButtonUi
import java.text.SimpleDateFormat
import java.util.Locale

data class TrackUi(
    val trackName: String,
    val trackId: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String,
    val info: List<InfoOption>,
    val trackUrl: String,
    val favouriteButtonUi: FavouriteButtonUi
) {
    companion object {
        fun mapFrom(entity: Track, toggleFavouriteCallback: (Track) -> Unit): TrackUi {
            return TrackUi(
                trackId = entity.trackId,
                trackName = entity.trackName,
                artistName = entity.artistName,
                trackTime = SimpleDateFormat(
                    "mm:ss",
                    Locale.getDefault()
                ).format(entity.trackTimeMillis),
                artworkUrl100 = entity.artworkUrl100,
                info = InfoOption.mapToList(entity),
                trackUrl = entity.previewUrl,
                favouriteButtonUi = FavouriteButtonUi.mapFrom(entity, toggleFavouriteCallback)
            )
        }
    }
}