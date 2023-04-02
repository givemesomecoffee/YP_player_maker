package givemesomecoffee.ru.playlistmaker.feature.track_card.utils

import android.app.Activity
import kotlin.reflect.KProperty

interface PlayerApi {
    operator fun getValue(trackCardActivity: Activity, property: KProperty<*>): PlayerApi
    fun startPlayer()
    fun pausePlayer()
}