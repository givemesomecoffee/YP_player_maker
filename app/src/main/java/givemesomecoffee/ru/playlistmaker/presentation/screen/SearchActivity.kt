package givemesomecoffee.ru.playlistmaker.presentation.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.presentation.utils.initSecondaryScreen

class SearchActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initSecondaryScreen(getString(R.string.title_search))
    }
}