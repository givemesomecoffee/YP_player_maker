package givemesomecoffee.ru.playlistmaker.core.domain.playlist

data class PlayListModel(
    override val id: Long? = null,
    override val name: String = "",
    override val path: String? = null,
    override val tracks: List<String> = emptyList(),
    override val size: Int = 0,
    override val description: String = "",
) : Playlist