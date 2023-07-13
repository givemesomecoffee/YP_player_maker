package givemesomecoffee.ru.playlistmaker.core.navigation

import android.os.Bundle
import givemesomecoffee.ru.playlistmaker.R

sealed class Actions(
    val id: Int,
    val bundle: Bundle?
) {

    class ToTrackCard(private val trackId: String, private val trackUrl: String, private val isFavourite: Boolean) : Actions(
        R.id.action_global_trackCardActivity, Bundle().apply {
            putString(Screens.TrackCard.ID_ARG_NAME, trackId)
            putString(Screens.TrackCard.TRACK_URL, trackUrl)
            putBoolean(Screens.TrackCard.IS_FAVOURITE, isFavourite)
        }
    )
}