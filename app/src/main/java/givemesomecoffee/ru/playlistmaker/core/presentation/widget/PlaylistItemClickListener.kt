package givemesomecoffee.ru.playlistmaker.core.presentation.widget

import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist

interface PlaylistItemClickListener {
    fun onItemClicked(playlist: Playlist)
}