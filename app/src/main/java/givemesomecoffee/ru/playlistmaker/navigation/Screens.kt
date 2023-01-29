package givemesomecoffee.ru.playlistmaker.navigation

import android.app.Activity
import givemesomecoffee.ru.playlistmaker.presentation.screen.MediaActivity
import givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.SearchActivity
import givemesomecoffee.ru.playlistmaker.presentation.screen.SettingsActivity

sealed class Screens(
    val screenClass: Class<out Activity>
) {
    object Search: Screens(SearchActivity::class.java)
    object Media: Screens(MediaActivity::class.java)
    object Settings: Screens(SettingsActivity::class.java)


}