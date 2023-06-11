package givemesomecoffee.ru.playlistmaker.core.navigation

import android.os.Bundle
import givemesomecoffee.ru.playlistmaker.R

sealed class Actions(
    val id: Int,
    val bundle: Bundle?
) {

    class SearchToTrackCard(private val trackId: String, private val trackUrl: String) : Actions(
        R.id.action_searchFragment_to_trackCardActivity, Bundle().apply {
            putString(Screens.TrackCard.ID_ARG_NAME, trackId)
            putString(Screens.TrackCard.TRACK_URL, trackUrl)
        }
    )
}