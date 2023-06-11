package givemesomecoffee.ru.playlistmaker.feature.media

import androidx.fragment.app.Fragment
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.navigation.TabScreen
import givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.FavoritesFragment
import givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.PlaylistsFragment

object MediaScreens {
    object Playlist : TabScreen {
        override fun getFragment(): Fragment = PlaylistsFragment.newInstance()
        override fun getTitleRes(): Int = R.string.playlists_title
    }

    object Favourites : TabScreen {
        override fun getFragment(): Fragment = FavoritesFragment.newInstance()
        override fun getTitleRes(): Int = R.string.favourites_title

    }
}