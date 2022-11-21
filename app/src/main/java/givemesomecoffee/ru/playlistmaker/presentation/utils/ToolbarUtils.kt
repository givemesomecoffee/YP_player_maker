package givemesomecoffee.ru.playlistmaker.presentation.utils

import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.initSecondaryScreen(title: String){
    supportActionBar?.apply {
        this.title = title
        setDisplayHomeAsUpEnabled(true)
        elevation = 0f
    }
}
