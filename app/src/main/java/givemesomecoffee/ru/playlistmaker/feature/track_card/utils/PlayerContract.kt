package givemesomecoffee.ru.playlistmaker.feature.track_card.utils

interface PlayerContract {
    fun onProgressChanged(text: String)
    fun stateChanged(state: PlayerState)
    fun obtainUrl(): String
}