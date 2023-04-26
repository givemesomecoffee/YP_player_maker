package givemesomecoffee.ru.playlistmaker.core.presentation.utils

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import givemesomecoffee.ru.playlistmaker.R

fun Activity.validateEvent(intent: Intent){
    try {
        startActivity(intent)
    } catch (e: Exception){
        Toast.makeText(this, getString(R.string.error) , Toast.LENGTH_SHORT).show()
    }
}