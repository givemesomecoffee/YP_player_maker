package givemesomecoffee.ru.playlistmaker.feature.track_card.model

import android.content.Context
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.data.tracks.model.Track
import java.text.SimpleDateFormat
import java.util.*

class InfoOption(
    val name: String,
    val value: String
) {
    companion object {
        fun mapToList(track: Track, context: Context): List<InfoOption> {
            return buildList {
                add(
                    InfoOption(
                        context.getString(R.string.track_duration),
                        SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
                    )
                )

                if (!track.collectionName.isNullOrEmpty()) {
                    add(
                        InfoOption(
                            context.getString(R.string.track_album),
                            track.collectionName
                        )
                    )
                }

                add(
                    InfoOption(
                        context.getString(R.string.track_year),
                        track.releaseDate.substring(0, 4)
                    )
                )
                add(InfoOption(context.getString(R.string.track_genre), track.primaryGenreName))
                add(InfoOption(context.getString(R.string.track_country), track.country))
            }
        }
    }
}
