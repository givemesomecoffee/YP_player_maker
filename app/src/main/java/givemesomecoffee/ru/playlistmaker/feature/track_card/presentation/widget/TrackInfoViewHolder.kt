package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.widget

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.feature.track_card.model.InfoOption

class TrackInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: InfoOption) {
        itemView.findViewById<TextView>(R.id.track_description_name).text =
            itemView.resources.getString(model.name)
        itemView.findViewById<TextView>(R.id.track_description_value).text = model.value
    }
}
