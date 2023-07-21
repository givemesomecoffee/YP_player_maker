package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.create_playlist.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.data.playlist.Playlist
import givemesomecoffee.ru.playlistmaker.core.presentation.widget.PlaylistItemClickListener

class PlaylistsAdapter(private val listener: PlaylistItemClickListener) :
    RecyclerView.Adapter<PlaylistViewHolder>() {
    var tracks = listOf<Playlist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist_card, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(tracks[position], listener)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

}
