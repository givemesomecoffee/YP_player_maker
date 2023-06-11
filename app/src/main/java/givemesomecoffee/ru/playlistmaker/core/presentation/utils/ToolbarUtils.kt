package givemesomecoffee.ru.playlistmaker.core.presentation.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import givemesomecoffee.ru.playlistmaker.R

fun AppCompatActivity.initSecondaryScreen(title: String){
    supportActionBar?.apply {
        this.title = title
        setHomeAsUpIndicator(R.drawable.ic_up_button)
        setDisplayHomeAsUpEnabled(true)
        elevation = 0f
    }
}

fun Fragment.initStartScreen(title: String){
    (requireActivity() as AppCompatActivity).supportActionBar?.apply {
        this.title = title
        setDisplayHomeAsUpEnabled(false)
        elevation = 0f
    }
}