package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.add_to_playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist
import givemesomecoffee.ru.playlistmaker.core.presentation.widget.PlaylistItemClickListener

class PlaylistsAdapter(private val listener: PlaylistItemClickListener) :
    RecyclerView.Adapter<PlaylistCellViewHolder>() {
    var tracks = listOf<Playlist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist_cell, parent, false)
        return PlaylistCellViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistCellViewHolder, position: Int) {
        holder.bind(tracks[position], listener)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

}