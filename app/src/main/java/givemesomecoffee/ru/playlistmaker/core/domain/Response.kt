package givemesomecoffee.ru.playlistmaker.core.domain

class Response<T>(
    val error: Throwable?,
    val content: T?
)