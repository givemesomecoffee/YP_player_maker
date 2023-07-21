package givemesomecoffee.ru.playlistmaker.core.presentation.utils

fun Int.resolveTracksCounter(): String {
    val postfix = (this % 10)
    return "$this " + when (postfix) {
        1 -> "трек"
        2, 3, 4 -> "трека"
        else -> "треков"
    }
}