package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.initSecondaryScreen
import givemesomecoffee.ru.playlistmaker.feature.media.MediaScreens
import givemesomecoffee.ru.playlistmaker.feature.media.ui.widget.MediaViewPagerAdapter

class MediaActivity : AppCompatActivity() {
    private lateinit var tabMediator: TabLayoutMediator
    private val tabs = listOf(MediaScreens.Favourites, MediaScreens.Playlist)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        initSecondaryScreen(getString(R.string.title_media))
        val vp = findViewById<ViewPager2>(R.id.viewPager)
        vp.adapter = MediaViewPagerAdapter(supportFragmentManager, lifecycle, tabs)
        tabMediator = TabLayoutMediator(findViewById(R.id.tabLayout), vp) { tab, position ->
            tab.text = resources.getString(tabs[position].getTitleRes())
        }
        tabMediator.attach()

    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}