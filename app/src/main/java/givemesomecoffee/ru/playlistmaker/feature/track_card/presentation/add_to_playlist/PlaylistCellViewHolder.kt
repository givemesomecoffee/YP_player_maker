package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.add_to_playlist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.data.playlist.Playlist
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.dpToPx
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.resolveTracksCounter
import givemesomecoffee.ru.playlistmaker.core.presentation.widget.PlaylistItemClickListener

class PlaylistCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.playlist_name)
    private val image: ImageView = itemView.findViewById(R.id.playlist_image)
    private val size: TextView = itemView.findViewById(R.id.playlist_size)

    fun bind(model: Playlist, listener: PlaylistItemClickListener) {
        itemView.rootView.setOnClickListener {
            listener.onItemClicked(model)
        }
        name.text = model.name
        size.text = model.size.resolveTracksCounter()
        Glide.with(itemView)
            .load(model.path)
            .fitCenter().transform(RoundedCorners(itemView.context.dpToPx(8)))
            .placeholder(R.drawable.ic_placeholder).into(image)
    }
}
