package givemesomecoffee.ru.playlistmaker.feature.track_card.model

import givemesomecoffee.ru.playlistmaker.core.presentation.player.PlayerStateUi

class TrackCardScreenState(
    val loading: Boolean,
    val data: TrackUi?,
    val playerState: PlayerStateUi? = null
)