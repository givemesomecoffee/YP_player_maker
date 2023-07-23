package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation

import androidx.annotation.DrawableRes
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.Track

data class FavouriteButtonUi(
    @DrawableRes val icon: Int,
    val callback: () -> Unit,

    ) {
    companion object {
        fun mapFrom(
            track: Track,
            toggleFavouriteCallback: (Track) -> Unit
        ): FavouriteButtonUi {
            return FavouriteButtonUi(
                if (track.isFavourite) R.drawable.ic_like_filled else R.drawable.ic_like
            ) { toggleFavouriteCallback(track) }
        }
    }
}