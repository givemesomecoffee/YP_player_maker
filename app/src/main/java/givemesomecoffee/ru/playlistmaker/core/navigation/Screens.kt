package givemesomecoffee.ru.playlistmaker.core.navigation

import android.app.Activity
import givemesomecoffee.ru.playlistmaker.feature.media.MediaActivity
import givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.SearchActivity
import givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.TrackCardActivity
import givemesomecoffee.ru.playlistmaker.feature.settings.SettingsActivity

sealed class Screens(
    val screenClass: Class<out Activity>
) {
    object Search : Screens(SearchActivity::class.java)
    object Media : Screens(MediaActivity::class.java)
    object Settings : Screens(SettingsActivity::class.java)

    class TrackCard(val id: String, val trackUrl: String) : Screens(TrackCardActivity::class.java) {
        companion object {
            const val ID_ARG_NAME = "id"
            const val TRACK_URL = "track_url"
        }
    }

}