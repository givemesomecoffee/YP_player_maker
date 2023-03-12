package givemesomecoffee.ru.playlistmaker.feature.track_card.widget

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class TrackInfoItemDecoration(
    private val verticalOffsetPx: Int,
    private val horizontalOffsetPx: Int
) : ItemDecoration() {

    constructor(@DimenRes verticalOffset: Int? = null, @DimenRes horizontalOffset: Int? = null, context: Context) : this(
        verticalOffset?.let { context.resources.getDimensionPixelOffset(it) } ?: 0,
        horizontalOffset?.let { context.resources.getDimensionPixelOffset(it) } ?: 0
    )

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(
            outRect.left + horizontalOffsetPx,
            outRect.top + verticalOffsetPx,
            outRect.right + horizontalOffsetPx,
            outRect.bottom + verticalOffsetPx
        )
    }

}