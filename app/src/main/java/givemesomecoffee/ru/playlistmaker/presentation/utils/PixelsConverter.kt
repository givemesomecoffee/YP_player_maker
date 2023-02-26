package givemesomecoffee.ru.playlistmaker.presentation.utils

import android.content.Context
import kotlin.math.roundToInt

fun Context.dpToPx(dp: Int): Int {
    resources.displayMetrics
    return (dp * resources.displayMetrics.density).roundToInt()
}