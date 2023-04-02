package givemesomecoffee.ru.playlistmaker.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.goToScreen(screen: Screens) {
    val navigationIntent = Intent(this, screen.screenClass)
    when (screen) {
        is Screens.TrackCard -> {
            navigationIntent.putExtra(Screens.TrackCard.ID_ARG_NAME, screen.id)
            navigationIntent.putExtra(Screens.TrackCard.TRACK_URL, screen.trackUrl)
        }
        else -> {}
    }
    startActivity(navigationIntent)
}
