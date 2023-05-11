package givemesomecoffee.ru.playlistmaker.core.presentation.utils

import androidx.appcompat.app.AppCompatActivity
import givemesomecoffee.ru.playlistmaker.R

fun AppCompatActivity.initSecondaryScreen(title: String){
    supportActionBar?.apply {
        this.title = title
        setHomeAsUpIndicator(R.drawable.ic_up_button)
        setDisplayHomeAsUpEnabled(true)
        elevation = 0f
    }
}
