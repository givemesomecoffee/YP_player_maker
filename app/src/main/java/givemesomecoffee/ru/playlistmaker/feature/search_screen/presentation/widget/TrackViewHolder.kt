package givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.widget

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.Track
import kotlin.math.roundToInt

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val trackTitle: TextView = itemView.findViewById(R.id.track_title)
    private val trackInfoArtist: TextView = itemView.findViewById(R.id.track_info_artist)
    private val trackInfoDuration: TextView = itemView.findViewById(R.id.track_info_duration)
    private val trackImage: ImageView = itemView.findViewById(R.id.track_image)

    fun bind(model: Track) {
        trackTitle.text = model.trackName
        trackInfoArtist.text = model.artistName
        trackInfoDuration.text = model.trackTime


        Glide.with(itemView).load(model.artworkUrl100).fitCenter()
            .transform(RoundedCorners(itemView.context.dpToPx(2)))
            .placeholder(R.drawable.ic_placeholder).into(trackImage)
    }


}
fun Context.dpToPx(dp: Int): Int {
    resources.displayMetrics
    return (dp * resources.displayMetrics.density).roundToInt()
}