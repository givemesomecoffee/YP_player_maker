package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.feature.track_card.model.InfoOption

class TrackInfoAdapter() : RecyclerView.Adapter<TrackInfoViewHolder>() {
    var info = listOf<InfoOption>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track_description, parent, false)
        return TrackInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackInfoViewHolder, position: Int) {
        holder.bind(info[position])
    }

    override fun getItemCount(): Int {
        return info.size
    }

}