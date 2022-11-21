package givemesomecoffee.ru.playlistmaker.presentation.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.navigation.Screens
import givemesomecoffee.ru.playlistmaker.navigation.goToScreen
import givemesomecoffee.ru.playlistmaker.presentation.view.main_menu_item.MainMenuItem

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.elevation = 0f
        findViewById<MainMenuItem>(R.id.menu_search)?.setOnClickListener{
            goToScreen(Screens.Search)
        }
        findViewById<MainMenuItem>(R.id.menu_media)?.setOnClickListener {
            goToScreen(Screens.Media)
        }
        findViewById<MainMenuItem>(R.id.menu_settings)?.setOnClickListener{
            goToScreen(Screens.Settings)
        }
    }
}