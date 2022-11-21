package givemesomecoffee.ru.playlistmaker.presentation.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import givemesomecoffee.ru.playlistmaker.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.elevation = 0f
    }
}