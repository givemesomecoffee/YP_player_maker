package givemesomecoffee.ru.playlistmaker.core.domain

data class Response<T>(
    val error: Throwable?,
    val content: T?
)