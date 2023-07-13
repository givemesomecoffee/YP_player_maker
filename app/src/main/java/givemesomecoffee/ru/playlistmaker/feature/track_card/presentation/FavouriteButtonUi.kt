package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation

import androidx.annotation.DrawableRes
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.data.tracks.model.TrackEntity

data class FavouriteButtonUi(
    @DrawableRes val icon: Int,
    val callback: () -> Unit,

    ) {
    companion object {
        fun mapFrom(
            track: TrackEntity,
            toggleFavouriteCallback: (TrackEntity) -> Unit
        ): FavouriteButtonUi {
            return FavouriteButtonUi(
                if (track.isFavourite) R.drawable.ic_like_filled else R.drawable.ic_like
            ) { toggleFavouriteCallback(track) }
        }
    }
}