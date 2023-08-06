package givemesomecoffee.ru.playlistmaker.core.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

object Screens {
    object TrackCard {
        const val ID_ARG_NAME = "id"
        const val TRACK_URL = "track_url"
        const val IS_FAVOURITE = "is_favourite"
    }

    object AddToPlayList{
        const val TRACK_ID = "id"
    }

    object PlaylistCard{
        const val PLAYLIST_ID = "playlist_id"
    }

    @Parcelize
    sealed interface EditPlayListArgs: Parcelable{

        @Parcelize
        data class Edit(val id: Long): EditPlayListArgs, Parcelable

        @Parcelize
        object Create: EditPlayListArgs, Parcelable

        companion object{
            const val TYPE = "type"
        }
    }
}