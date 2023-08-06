package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.create_playlist.widget

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.dpToPx
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.resolveTracksCounter
import givemesomecoffee.ru.playlistmaker.core.presentation.widget.PlaylistItemClickListener

class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.playlist_name)
    private val image: ImageView = itemView.findViewById(R.id.playlist_image)
    private val size: TextView = itemView.findViewById(R.id.playlist_size)
    private val placeholder: ImageView = itemView.findViewById(R.id.playlist_placeholder)

    fun bind(model: Playlist, listener: PlaylistItemClickListener) {
        name.text = model.name
        size.text = model.size.resolveTracksCounter()
        val hasImage = !model.path.isNullOrEmpty()
        placeholder.isVisible = !hasImage
        if (hasImage) {
            Glide.with(itemView)
                .load(model.path)
                .transform(CenterCrop(), RoundedCorners(itemView.context.dpToPx(8)))
                .placeholder(R.drawable.ic_placeholder)
                .into(image)
        }
    }
}
