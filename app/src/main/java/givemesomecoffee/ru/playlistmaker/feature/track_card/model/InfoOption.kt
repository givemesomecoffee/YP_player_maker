package givemesomecoffee.ru.playlistmaker.feature.track_card.model

import androidx.annotation.StringRes
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track
import java.text.SimpleDateFormat
import java.util.Locale

class InfoOption(
    @StringRes val name: Int,
    val value: String
) {
    companion object {
        fun mapToList(track: Track): List<InfoOption> {
            return buildList {
                add(
                    InfoOption(
                        R.string.track_duration,
                        SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
                    )
                )

                track.collectionName?.let {
                    add(
                        InfoOption(
                            R.string.track_album,
                            it
                        )
                    )
                }

                add(
                    InfoOption(
                        R.string.track_year,
                        track.releaseDate?.substring(0, 4).orEmpty()
                    )
                )
                add(InfoOption(R.string.track_genre, track.primaryGenreName))
                add(InfoOption(R.string.track_country, track.country))
            }
        }
    }
}
