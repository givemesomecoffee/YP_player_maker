package givemesomecoffee.ru.playlistmaker.core.presentation.utils

import android.content.Context
import kotlin.math.roundToInt

fun Context.dpToPx(dp: Int): Int {
    resources.displayMetrics
    return (dp * resources.displayMetrics.density).roundToInt()
}