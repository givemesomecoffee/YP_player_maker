package givemesomecoffee.ru.playlistmaker.core.presentation.widget

import givemesomecoffee.ru.playlistmaker.core.data.playlist.Playlist

interface PlaylistItemClickListener {
    fun onItemClicked(playlist: Playlist)
}