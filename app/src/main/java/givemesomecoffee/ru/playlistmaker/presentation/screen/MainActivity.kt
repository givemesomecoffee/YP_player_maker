package givemesomecoffee.ru.playlistmaker.presentation.screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.presentation.view.main_menu_item.MainMenuItem

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.elevation = 0f

        val menuClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                showPlaceholder(v)
            }
        }

        findViewById<MainMenuItem>(R.id.menu_search)?.setOnClickListener(menuClickListener)
        findViewById<MainMenuItem>(R.id.menu_media)?.setOnClickListener {
            showPlaceholder(it)
        }
        findViewById<MainMenuItem>(R.id.menu_settings)?.setOnClickListener(menuClickListener)
    }

    private fun showPlaceholder(v: View?){
        Toast.makeText(
            this,
            (v as? MainMenuItem)?.getTitle() ?: getString(R.string.error),
            Toast.LENGTH_SHORT
        ).show()
    }
}