package givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.widget

import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TrackUi

interface ItemClickListener {
    fun onTrackClicked(track: TrackUi)
}