package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.feature.media.MediaScreens
import givemesomecoffee.ru.playlistmaker.feature.media.ui.widget.MediaViewPagerAdapter

class MediaFragment : Fragment(R.layout.fragment_media) {

    private lateinit var tabMediator: TabLayoutMediator
    private val tabs = listOf(MediaScreens.Favourites, MediaScreens.Playlist)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vp = view.findViewById<ViewPager2>(R.id.viewPager)
        vp.adapter = MediaViewPagerAdapter(childFragmentManager, lifecycle, tabs)
        tabMediator = TabLayoutMediator(view.findViewById(R.id.tabLayout), vp) { tab, position ->
            tab.text = resources.getString(tabs[position].getTitleRes())
        }
        tabMediator.attach()
    }

    override fun onDestroyView() {
        tabMediator.detach()
        super.onDestroyView()
    }
}