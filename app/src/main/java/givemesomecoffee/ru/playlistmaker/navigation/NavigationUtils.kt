package givemesomecoffee.ru.playlistmaker.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.goToScreen(screen: Screens) {
    val navigationIntent = Intent(this, screen.screenClass)
    startActivity(navigationIntent)
}
